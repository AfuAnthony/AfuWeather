package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.weather.entity.HeWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("api/weather")
   suspend fun getWeather(@Query("cityid") weatherId: String): HeWeather

    @GET("api/bing_pic")
   suspend fun getBingPic(): String

}