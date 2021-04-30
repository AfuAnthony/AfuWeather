package com.anthonyh.afuweather.mvp.place.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
class City ( @SerializedName("name") val cityName: String, @SerializedName("id") val cityCode: Int)  {
    @PrimaryKey
    var provinceId = 0
}