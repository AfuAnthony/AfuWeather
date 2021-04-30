package com.anthonyh.afuweather.mvvm.weather.repository

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class QueryWeatherRepository {


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