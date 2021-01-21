package com.anthonyh.afuweather.weather.entity

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

class HeWeather {

    @SerializedName("HeWeather")
    var weather: List<Weather>? = null

    override fun toString(): String {
        return weather?.run {
            val stringBuilder = StringBuilder()
            forEach {
                stringBuilder.append(it?.toString()).append("\r\n")
            }
            stringBuilder.toString()
        }!!

    }
}