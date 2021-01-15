package com.anthonyh.afuweather.place.entity

import com.google.gson.annotations.SerializedName

class Province (@SerializedName("name") val provinceName: String, @SerializedName("id") val provinceCode: Int)  {
    @Transient val id = 0
}