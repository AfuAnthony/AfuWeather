package com.anthonyh.afuweather.weather.entity

import com.google.gson.annotations.SerializedName

data class Weather constructor(var status:String = "") {

    //    var status = ""
    lateinit var basic: Basic
    lateinit var aqi: AQI
    lateinit var now: Now
    lateinit var suggestion: Suggestion

    @SerializedName("daily_forecast")
    lateinit var forecastList: List<Forecast>


}