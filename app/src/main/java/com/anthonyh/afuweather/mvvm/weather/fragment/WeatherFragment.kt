package com.anthonyh.afuweather.mvvm.weather.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.AMap.OnCameraChangeListener
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.model.*
import com.amap.api.maps.model.animation.Animation
import com.amap.api.maps.model.animation.TranslateAnimation
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.help.Tip
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.application.WeatherApplication
import com.anthonyh.afuweather.databinding.FragmentChoosePlaceBindingImpl
import com.anthonyh.afuweather.mvvm.weather.model.ChoosePlaceViewModel
import com.anthonyh.afuweather.mvvm.weather.model.QueryWeatherViewModel
import com.anthonyh.afuweather.mvvm.weather.repository.Status
import com.anthonyh.afuweather.util.InjectorUtil
import com.anthonyh.afuweather.util.dip2px
import com.anthonyh.afuweather.util.format
import com.anthonyh.afuweather.util.SixFormatString
import kotlinx.android.synthetic.main.fragment_choose_place.*
import java.util.*

/**
@author Anthony.H
@date: 2021/4/28
@desription:
 */
class WeatherFragment : Fragment(), LocationSource, AMapLocationListener {

    companion object {
        private const val TAG = "WeatherFragment"
    }

    private val choosePlaceViewModel by viewModels<ChoosePlaceViewModel>()
    private val queryWeatherViewModel by viewModels<QueryWeatherViewModel>(factoryProducer = { InjectorUtil.getQueryWeatherViewModelFactory() })

    private var aMap: AMap? = null
    private var locationMarker: Marker? = null
    private var locationClient: AMapLocationClient? = null
    private var locationChangedListener: OnLocationChangedListener? = null
    private var tipList: List<Tip>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_place, container, false)
        val bindingImp = DataBindingUtil.bind<FragmentChoosePlaceBindingImpl>(view)
        bindingImp?.choosePlaceFragment = this
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map_view.onCreate(savedInstanceState)
        init()
        observeData()
    }

    private fun init() {


        aMap = map_view.map
        aMap?.run {
            uiSettings.isZoomControlsEnabled = false
            setLocationSource(this@WeatherFragment) // 设置定位监听
            uiSettings.isMyLocationButtonEnabled = false // 设置默认定位按钮是否显示
            isMyLocationEnabled = true // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            setMyLocationType(AMap.LOCATION_TYPE_LOCATE)
        }
        aMap?.run {
            setOnCameraChangeListener(object : OnCameraChangeListener {
                override fun onCameraChange(cameraPosition: CameraPosition) {}
                override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
                    choosePlaceViewModel.parsePlaceCode(
                        LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude)
                    )
                    startJumpAnimation()
                }
            })
            setOnMapLoadedListener {
                val latLng = aMap!!.cameraPosition.target
                val screenPosition = aMap!!.projection.toScreenLocation(latLng)
                locationMarker = aMap!!.addMarker(
                    MarkerOptions()
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.purple_pin))
                )
                //设置Marker在屏幕上,不跟随地图移动
                locationMarker?.setPositionByPixels(screenPosition.x, screenPosition.y)
                locationMarker?.zIndex = 1f
            }
        }
    }


    private fun observeData() {
        //智能提示搜索监听
        choosePlaceViewModel.autoListData.observe(viewLifecycleOwner) {
            val list = it
            tipList = it
            val listString: MutableList<String> = ArrayList<String>()
            for (i in list.indices) {
                listString.add(list[i].name)
            }
            val aAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                WeatherApplication.context,
                R.layout.route_inputs, listString
            )
            key_word.setAdapter(aAdapter)
            aAdapter.notifyDataSetChanged()
            key_word.showDropDown()
        }

        choosePlaceViewModel.realPlaceData.observe(viewLifecycleOwner) {
            it?.run {
                key_word.setText("")
                //获取到地址
                Log.e(TAG, "observeData: ${it.regeocodeAddress.district}}")
                queryWeatherViewModel.queryWeather(
                    it.regeocodeQuery.point.longitude,
                    it.regeocodeQuery.point.latitude,
                    "${it.regeocodeAddress.city}${it.regeocodeAddress.district}"
                )
            }
        }

        //错误监听
        choosePlaceViewModel.errorData.observe(viewLifecycleOwner) {
            it?.run {
                Toast.makeText(WeatherApplication.context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        //////
        queryWeatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "observeData: ${it.status}")
            when (it.status) {
                Status.LOADING -> {
                    Log.e(TAG, "observeData: 正在查询天气")
                }
                Status.ERROR -> {
                    Log.e(TAG, "observeData: ${it.message}")
                }
                Status.SUCCESS -> {
                    Log.e(
                        TAG,
                        "${it.data?.weatherDataJson}}"
                    )
                    it.data?.let { weatherData -> point_recycleView.refresh(weatherData.convertJson()) }

                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        map_view.onPause()
        deactivate()
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_view.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
        locationClient?.run {
            stopLocation()
            onDestroy()
        }
        locationChangedListener = null
    }


    override fun activate(listener: LocationSource.OnLocationChangedListener?) {
        locationChangedListener = listener
        if (locationClient == null) {
            locationClient = AMapLocationClient(WeatherApplication.context)
            locationClient?.run {
                val amapLocationClient = AMapLocationClientOption()
                //设置定位监听
                setLocationListener(this@WeatherFragment)
                //设置为高精度定位模式
                amapLocationClient?.isOnceLocation = true
                amapLocationClient?.locationMode =
                    AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                //设置定位参数
                setLocationOption(amapLocationClient)
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                startLocation()
            }
        }
    }

    override fun deactivate() {
        locationChangedListener = null
        locationClient?.run {
            stopLocation()
            onDestroy()
        }
        locationClient = null
    }

    /**
     * 屏幕中心marker 跳动
     */
    fun startJumpAnimation() {
        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            val latLng = locationMarker!!.position
            val point = aMap!!.projection.toScreenLocation(latLng)
            point.y -= requireContext().dip2px(125f).toInt()
            val target = aMap!!.projection
                .fromScreenLocation(point)
            //使用TranslateAnimation,填写一个需要移动的目标点
            val animation: Animation = TranslateAnimation(target)
            animation.setInterpolator { input -> // 模拟重加速度的interpolator
                if (input <= 0.5) {
                    (0.5f - 2 * (0.5 - input) * (0.5 - input)).toFloat()
                } else {
                    (0.5f - Math.sqrt(((input - 0.5f) * (1.5f - input)).toDouble())).toFloat()
                }
            }
            //整个移动所需要的时间
            animation.setDuration(600)
            //设置动画
            locationMarker!!.setAnimation(animation)
            //开始动画
            locationMarker!!.startAnimation()
        }
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null && amapLocation.errorCode == 0) {
            locationChangedListener?.onLocationChanged(amapLocation)
            val curLatlng = LatLng(amapLocation.latitude, amapLocation.longitude)
            aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f))
        }
    }

    val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            s?.run {
                choosePlaceViewModel.autoSearch(this.toString())
            }
        }
    }
    val onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        tipList?.run {
            val point = get(position).point
            aMap!!.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        point.latitude, point
                            .longitude
                    ), 16f
                )
            )
        }
    }
}