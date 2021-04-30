package com.anthonyh.afuweather.mvp.weather

import com.anthonyh.afuweather.mvp.base.BasePresenter
import com.anthonyh.afuweather.mvp.base.BaseView
import com.anthonyh.afuweather.mvp.weather.entity.HeWeather
import java.lang.Exception

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
interface WeatherContract {

    interface IWeatherView : BaseView {
        fun requestWeather(cityId: String)
        fun onWeatherResultCallBack(result: HeWeather?)
        fun requestPingPic()
        fun onGetPingPic(url: String)
        fun onError(e: Exception?)
    }

    abstract class BaseWeatherPresenter : BasePresenter<IWeatherView>() {
        abstract fun requestWeather(cityId: String)
        abstract fun requestBingPicUrl()
    }

    interface IWeatherModel {
        suspend fun requestWeather(cityId: String): HeWeather?
        suspend fun requestBingPicUrl(): String?
    }

}