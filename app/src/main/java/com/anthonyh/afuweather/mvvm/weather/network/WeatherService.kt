package com.anthonyh.afuweather.mvvm.weather.network

import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.application.WeatherApplication
import com.anthonyh.afuweather.common.ApiResponse
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
interface WeatherService {

    @GET("v2.5/{appKey}/{location}/weather.json")
    fun queryWeather(
        @Path("appKey") appKey: String = WeatherApplication.context.resources.getString(R.string.caiyun_appkey),
        @Path("location") location: String
    ): Flow<ApiResponse<CaiYunWeather>>
}