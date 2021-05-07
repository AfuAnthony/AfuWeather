package com.anthonyh.afuweather.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import com.anthonyh.afuweather.util.GsonUtil

/**
@author Anthony.H
@date: 2021/5/6
@desription:天气本地缓存表
 */
@Entity
data class WeatherData(

    @PrimaryKey//经纬度，用来标识数据的唯一性
    val locationTude: String,
    val weatherDataJson: String
//    val caiYunWeather: CaiYunWeather,//天气数据内容
) {
    fun convertJson() = GsonUtil.fromJson(weatherDataJson, CaiYunWeather::class.java)
}