package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.common.RetrofitManager
import com.anthonyh.afuweather.weather.entity.HeWeather

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
class WeatherPresenter : WeatherContract.BaseWeatherPresenter() {

    private var model: WeatherModelImpl = WeatherModelImpl()

    private var weatherService = RetrofitManager.createService(WeatherService::class.java)
    override fun requestWeather(): HeWeather? {
        return null
    }


}