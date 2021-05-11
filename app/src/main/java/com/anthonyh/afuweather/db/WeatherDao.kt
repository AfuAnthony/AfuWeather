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
    //根据经纬度查询缓存数据，
    /**
     * 注意：除非已对构建器调用 allowMainThreadQueries()，否则 Room 不支持在主线程上访问数据库，因为它可能会长时间锁定界面。
     * 异步查询（返回 LiveData 或 Flowable 实例的查询）无需遵守此规则，因为此类查询会根据需要在后台线程上异步运行查询。
     * 也就是说，返回LiveData的查询，自动支持在后台线程查询
     */
    @Query("SELECT * FROM WeatherData WHERE locationTude=:locationTude")
      fun queryWeatherByLocation(locationTude: String): Flow<WeatherData>//Flow就像rxjava里面那个


}