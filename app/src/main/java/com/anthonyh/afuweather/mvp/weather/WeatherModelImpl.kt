package com.anthonyh.afuweather.mvp.weather

import com.anthonyh.afuweather.common.RetrofitManager
import com.anthonyh.afuweather.mvp.weather.entity.HeWeather

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
//            //先查缓存，
//            //如果缓存没有再从网络获取
 */

class WeatherModelImpl : WeatherContract.IWeatherModel {

    private var weatherService = RetrofitManager.createService(WeatherService::class.java)

    override suspend fun requestWeather(cityId: String): HeWeather? {
        return weatherService.getWeather(cityId)
    }

    override suspend fun requestBingPicUrl(): String {
        return weatherService.getBingPic()
    }


}