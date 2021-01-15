package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.base.BasePresenter
import com.anthonyh.afuweather.base.BaseView
import com.anthonyh.afuweather.weather.entity.HeWeather

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
interface WeatherContract {

    interface WeatherView:BaseView {
        fun requestWeather(): HeWeather?
    }

    abstract class BaseWeatherPresenter:BasePresenter<WeatherView>() {
       abstract fun requestWeather(): HeWeather?
    }

    interface WeatherModel {
        fun requestWeather(): HeWeather?
    }

}