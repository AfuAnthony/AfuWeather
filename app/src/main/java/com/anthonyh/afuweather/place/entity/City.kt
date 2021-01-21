package com.anthonyh.afuweather.place.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
class City ( @SerializedName("name") val cityName: String, @SerializedName("id") val cityCode: Int)  {
    var provinceId = 0
}