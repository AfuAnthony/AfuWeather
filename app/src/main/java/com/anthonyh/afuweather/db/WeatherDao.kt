package com.anthonyh.afuweather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
@author Anthony.H
@date: 2021/5/6
@desription:
 */

@Dao
interface WeatherDao {
    //插入缓存
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(weatherCache: WeatherData)

    //根据经纬度查询缓存数据
    @Query("SELECT * FROM WeatherData WHERE locationTude=:locationTude")
    fun queryWeatherByLocation(locationTude: String): LiveData<WeatherData>//Flow就像rxjava里面那个


}