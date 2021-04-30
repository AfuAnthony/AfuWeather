package com.anthonyh.afuweather.mvp.weather

import android.util.Log
import android.widget.Toast
import com.anthonyh.afuweather.mvp.base.BaseActivity
import com.anthonyh.afuweather.databinding.ActivityMainBinding
import com.anthonyh.afuweather.mvp.weather.entity.HeWeather
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class WeatherActivity :
    BaseActivity<WeatherContract.BaseWeatherPresenter, WeatherContract.IWeatherView, ActivityMainBinding>(),
    WeatherContract.IWeatherView {

    companion object {
        private const val TAG = "WeatherActivity"
    }

    override fun createPresenter(): WeatherPresenterImpl {
        return WeatherPresenterImpl()
    }

    override fun requestWeather(cityId: String) {
        presenter?.requestWeather(cityId)
    }

    override fun onLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun onDisLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun onWeatherResultCallBack(result: HeWeather?) {
        swipeRefresh.isRefreshing = false
        result?.run {
            val weather = result.weather?.get(0)
            viewBinding.weather = weather
        }
    }

    override fun requestPingPic() {
        presenter?.requestBingPicUrl()
    }

    override fun onGetPingPic(url: String) {
//        Log.e(TAG, "onGetPingPic: $url")
//        url?.run { Glide.with(applicationContext).load(url).into(bingPicImg) }
        url?.run { viewBinding.bindUrl = this }
    }

    override fun onError(e: Exception?) {
        swipeRefresh.isRefreshing = false
        e?.run {
            Log.e(TAG, "onError: ${e.message}")
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateInit() {
        getData()
        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private fun getData() {
        requestWeather("CN101010100")
        requestPingPic()
    }
}