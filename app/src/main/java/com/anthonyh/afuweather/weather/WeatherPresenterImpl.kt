package com.anthonyh.afuweather.weather

import android.util.Log
import com.anthonyh.afuweather.weather.entity.HeWeather
import kotlinx.coroutines.*
import java.lang.Exception

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
class WeatherPresenterImpl : WeatherContract.BaseWeatherPresenter() {

    companion object {
        private const val TAG = "WeatherPresenter"
    }

    private var model: WeatherModelImpl = WeatherModelImpl()

    override fun requestWeather(cityId: String) {
        CoroutineScope(job).launch {
            try {
                showLoading()
                val weatherResult = model.requestWeather(cityId)
                withContext(Dispatchers.Main) {
                    callBackResult(weatherResult)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                disLoading()
            }
        }
    }


    private fun callBackResult(weather: HeWeather?) {
        if (isNeedCallBack()) {
            view?.onWeatherResultCallBack(weather, null)
        }
        Log.e(TAG, "callBackResult: ${weather?.toString()}")
    }


}