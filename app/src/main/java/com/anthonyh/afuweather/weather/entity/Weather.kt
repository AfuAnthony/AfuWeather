package com.anthonyh.afuweather.weather.entity

import com.anthonyh.afuweather.weather.entity.*
import com.google.gson.annotations.SerializedName

class Weather {
    var status = ""
    lateinit var basic: Basic
    lateinit var aqi: AQI
    lateinit var now: Now
    lateinit var suggestion: Suggestion
    @SerializedName("daily_forecast")
    lateinit var forecastList: List<Forecast>
}