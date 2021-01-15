package com.anthonyh.afuweather.place.entity

import com.google.gson.annotations.SerializedName

class County (@SerializedName("name") val countyName: String, @SerializedName("weather_id") val weatherId: String)  {
    var cityId = 0
}