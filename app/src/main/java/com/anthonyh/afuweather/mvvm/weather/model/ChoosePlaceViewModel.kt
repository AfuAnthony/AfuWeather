package com.anthonyh.afuweather.mvvm.weather.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip
import com.anthonyh.afuweather.application.WeatherApplication
import com.anthonyh.afuweather.error.WeatherException

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class ChoosePlaceViewModel : ViewModel(), GeocodeSearch.OnGeocodeSearchListener {

    private val _autoListData = MutableLiveData<List<Tip>>()

    val autoListData: LiveData<List<Tip>>
        get() = _autoListData


    private val _errorData = MutableLiveData<WeatherException>()

    val errorData: LiveData<WeatherException>
        get() = _errorData

    private var geocoderSearch: GeocodeSearch? = null

    private val _realPlaceData = MutableLiveData<RegeocodeResult>()
    val realPlaceData: LiveData<RegeocodeResult>
        get() = _realPlaceData

    init {
        geocoderSearch = GeocodeSearch(WeatherApplication.context)
        geocoderSearch?.setOnGeocodeSearchListener(this)
    }

    /**
     *智能提示
     */
    fun autoSearch(keyWord: String?) {
        keyWord?.run {
            val newText: String = keyWord.toString().trim()
            if (newText.isNotEmpty()) {
                val inputquery = InputtipsQuery(newText, "")
                val inputTips = Inputtips(WeatherApplication.context, inputquery)
                inputTips.setInputtipsListener(inputSearchListener)
                inputTips.requestInputtipsAsyn()
            }
        }
    }


    private val inputSearchListener =
        Inputtips.InputtipsListener { list: List<Tip>, rCode: Int ->
            if (rCode == AMapException.CODE_AMAP_SUCCESS) { // 正确返回
                _autoListData.value = list
            } else {
                _errorData.value = WeatherException(rCode, "智能提示失败")
            }

        }

    fun parsePlaceCode(latLonPoint: LatLonPoint) {
        val query = RegeocodeQuery(
            latLonPoint,
            200f,
            GeocodeSearch.AMAP
        ) // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch!!.getFromLocationAsyn(query)
    }


    override fun onRegeocodeSearched(result: RegeocodeResult?, rCode: Int) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            result?.run {
                _realPlaceData.value = result
            }
        }
    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

    }


}