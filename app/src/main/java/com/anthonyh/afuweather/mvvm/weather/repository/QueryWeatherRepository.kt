package com.anthonyh.afuweather.mvvm.weather.repository

import androidx.lifecycle.LiveData
import com.anthonyh.afuweather.common.ApiResponse
import com.anthonyh.afuweather.common.AppExecutors
import com.anthonyh.afuweather.common.RetrofitManager
import com.anthonyh.afuweather.db.WeatherDao
import com.anthonyh.afuweather.db.WeatherData
import com.anthonyh.afuweather.mvvm.weather.network.WeatherService
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import kotlinx.coroutines.flow.Flow

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

    suspend fun queryWeather(
        longitude: Double,
        latitude: Double,
        locationName: String
    ) = object : NetworkBoundResource<WeatherData, CaiYunWeather>(AppExecutors()) {
        override fun saveCallResult(item: CaiYunWeather) {
            TODO("Not yet implemented")
        }

        override fun shouldFetch(data: WeatherData?): Boolean {
            TODO("Not yet implemented")
        }

        override fun loadFromDb(): LiveData<WeatherData> {
            return weatherDao.queryWeatherByLocation("$longitude,$latitude")
        }

        override fun createCall(): LiveData<ApiResponse<CaiYunWeather>> {

            val weatherService = RetrofitManager.createService(WeatherService::class.java)
            return weatherService.queryWeather(location = "$longitude,$latitude")
        }

    }.asLiveData()


    private fun refreshWeather(longitude: Double, latitude: Double, locationName: String) {
        weatherDao.queryWeatherByLocation("$longitude,$latitude")

    }


    companion object {
        private var instance: QueryWeatherRepository? = null
        fun getInstance(): QueryWeatherRepository? {
            if (instance == null) {

                synchronized(QueryWeatherRepository::class.java) {
                    if (instance == null) {
                        instance = QueryWeatherRepository()
                    }
                }
            }
            return instance
        }

    }

}