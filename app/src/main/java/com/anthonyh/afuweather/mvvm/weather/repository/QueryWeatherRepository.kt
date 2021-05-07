package com.anthonyh.afuweather.mvvm.weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
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
import java.text.DecimalFormat

/**
@author Anthony.H
@date: 2021/4/30
@desription:
根据经纬度和当前时间查询数据库中缓存数据，如果数据有效，则返回缓存数据，否则从网上获取数据，成功后返回并更新对应缓存
 */
class QueryWeatherRepository(private val weatherDao: WeatherDao) {


//    suspend fun queryWeather(
//        longitude: Double,
//        latitude: Double,
//        locationName: String
//    ): Flow<WeatherData> {
//        refreshWeather(longitude, latitude, locationName)
//
//        val weatherService = RetrofitManager.createService(WeatherService::class.java)
//        val weather = weatherService.queryWeather(location = "$longitude,$latitude")
//        weather.locationName = locationName
//
//        return weather
//    }


    fun queryWeather(
        longitude: Double,
        latitude: Double,
        locationName: String
    ) = object : NetworkBoundResource<WeatherData, CaiYunWeather>(
        AppExecutors(), {
            Log.d(TAG, "resultInterceptor: --->")
            it.locationName = locationName
            it
        }) {
        override fun saveCallResult(item: CaiYunWeather) {
//            Log.e(TAG, "saveCallResult: $item")
            weatherDao.saveWeather(
                WeatherData(
                    "${longitude},$latitude",
                    GsonUtil.toJson(item)
                )
            )
        }

        override fun shouldFetch(data: WeatherData?): Boolean {
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


        override fun loadFromDb(): LiveData<WeatherData> {
            Log.e(TAG, "loadFromDb:$longitude,$latitude")
            return weatherDao.queryWeatherByLocation("$longitude,$latitude")
        }

        override fun createCall(): LiveData<ApiResponse<CaiYunWeather>> {
            val weatherService =
                RetrofitManager.createService(
                    RetrofitManager.getRetrofit(RetrofitManager.CAIYUN)!!,
                    WeatherService::class.java
                )
            return weatherService.queryWeather(location = "$longitude,$latitude")
        }
    }.asLiveData()


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