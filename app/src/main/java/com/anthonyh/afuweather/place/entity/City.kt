package com.anthonyh.afuweather.place.entity

import com.google.gson.annotations.SerializedName

class City ( @SerializedName("name") val cityName: String, @SerializedName("id") val cityCode: Int)  {

    var provinceId = 0
}