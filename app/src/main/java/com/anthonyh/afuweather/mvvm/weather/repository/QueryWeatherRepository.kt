package com.anthonyh.afuweather.mvvm.weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.anthonyh.afuweather.application.WeatherApplication
import com.anthonyh.afuweather.common.ApiResponse
import com.anthonyh.afuweather.common.AppExecutors
import com.anthonyh.afuweather.common.RetrofitManager
import com.anthonyh.afuweather.db.AppDatabase
import com.anthonyh.afuweather.db.WeatherDao
import com.anthonyh.afuweather.db.WeatherData
import com.anthonyh.afuweather.mvvm.weather.network.WeatherService
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import com.anthonyh.afuweather.util.GsonUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.text.DecimalFormat

/**
@author Anthony.H
@date: 2021/4/30
@desription:
根据经纬度和当前时间查询数据库中缓存数据，如果数据有效，则返回缓存数据，否则从网上获取数据，成功后返回并更新对应缓存
 */
class QueryWeatherRepository(private val weatherDao: WeatherDao) {

    fun queryWeather(
        longitude: Double,
        latitude: Double,
        locationName: String,
    ) = object :
        NetworkBoundResource<WeatherData, CaiYunWeather>(resultInterceptor = { result ->
            result.locationName = locationName
            result
        }) {
        override suspend fun saveCallResult(item: CaiYunWeather) {
            Log.d(TAG, "saveCallResult: $item")
            weatherDao.saveWeather(
                WeatherData(
                    "${longitude},$latitude",
                    GsonUtil.toJson(item)
                )
            )
        }

        override fun shouldFetch(data: WeatherData?): Boolean {
            Log.e(TAG, "shouldFetch: ${data.toString()}")
            //判断数据库给出的数据是否过期来抉择是否重新从网络获取数据
            if (data != null) {
                val caiYunWeather =
                    GsonUtil.fromJson(data.weatherDataJson, CaiYunWeather::class.java)
                val deltaTime =
                    System.currentTimeMillis() - (caiYunWeather.serverTime!!.toLong() * 1000)
                Log.e(TAG, "shouldFetch:deltatime: $deltaTime/1000 s")
                return deltaTime > 1000 * 60 * 60
            }
            return true
        }


        override suspend fun loadFromDb(): WeatherData {
            Log.d(TAG, "loadFromDb:$longitude,$latitude")
            //用distinctUntilChanged包装一下，防止收到重复的值
            return weatherDao.queryWeatherByLocation("$longitude,$latitude")
        }

        override suspend fun createCall(): CaiYunWeather {
            val weatherService =
                RetrofitManager.createService(
                    RetrofitManager.getRetrofit(RetrofitManager.CAIYUN)!!,
                    WeatherService::class.java
                )
            return weatherService.queryWeather(location = "$longitude,$latitude")
        }
    }


    companion object {
        private const val TAG = "QueryWeatherRepository"
        private var instance: QueryWeatherRepository? = null
        fun getInstance(): QueryWeatherRepository? {
            if (instance == null) {
                synchronized(QueryWeatherRepository::class.java) {
                    if (instance == null) {

                        instance = QueryWeatherRepository(
                            AppDatabase.getInstance(WeatherApplication.context).weatherDao()
                        )
                    }
                }
            }
            return instance
        }

    }

}