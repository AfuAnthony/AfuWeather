package com.anthonyh.afuweather.mvp.weather

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

    private var model = WeatherModelImpl()

    override fun requestWeather(cityId: String) {
        CoroutineScope(job).launch {
            try {
                showLoading()
                val weatherResult = model.requestWeather(cityId)
                if (isNeedCallBack()) {
                    view?.onWeatherResultCallBack(weatherResult)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                view?.onError(e)
            } finally {
                disLoading()
            }
        }
    }

    override fun requestBingPicUrl() {
        CoroutineScope(job).launch {
            try {
                val url = model.requestBingPicUrl()
                if (isNeedCallBack()) {
                    view?.onGetPingPic(url)
                }
            } catch (e: Exception) {
                view?.onError(e)
            }
        }
    }


}