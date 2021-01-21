package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.base.BasePresenter
import com.anthonyh.afuweather.base.BaseView
import com.anthonyh.afuweather.weather.entity.HeWeather
import java.lang.Exception

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
interface WeatherContract {

    interface IWeatherView : BaseView {
        fun requestWeather(cityId: String)
        fun onWeatherResultCallBack(result: HeWeather?, e: Exception?)
    }

    abstract class BaseWeatherPresenter : BasePresenter<IWeatherView>() {
        abstract fun requestWeather(cityId: String)
    }

    interface IWeatherModel {
        suspend fun requestWeather(cityId: String): HeWeather?
    }

}