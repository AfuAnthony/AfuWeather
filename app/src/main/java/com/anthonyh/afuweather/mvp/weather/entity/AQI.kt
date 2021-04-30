package com.anthonyh.afuweather.mvp.weather.entity

class AQI {
    lateinit var city: AQICity

    inner class AQICity {
        var aqi = ""
        var pm25  = ""
    }
}