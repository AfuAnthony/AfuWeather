package com.anthonyh.afuweather.mvvm.weather.network

import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.application.WeatherApplication
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
interface WeatherService {

    @GET
    suspend fun queryWeather(
        @Url url: String = "https://api.caiyunapp.com/v2.5/{appKey}/{location}/weather.json",
        @Path("appKey") appKey: String = WeatherApplication.context.resources.getString(R.string.caiyun_appkey),
        @Path("location") location: String
    ): CaiYunWeather
}