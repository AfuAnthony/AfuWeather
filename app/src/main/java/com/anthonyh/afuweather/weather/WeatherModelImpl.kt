package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.common.RetrofitManager
import com.anthonyh.afuweather.weather.entity.HeWeather
import kotlinx.coroutines.coroutineScope

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
class WeatherModelImpl : WeatherContract.IWeatherModel {


    private var weatherService = RetrofitManager.createService(WeatherService::class.java)

    override suspend fun requestWeather(cityId: String): HeWeather? {
        return coroutineScope {
            //先查缓存，
            //如果缓存没有再从网络获取
            weatherService.getWeather(cityId)//CN101010100
        }
    }

}