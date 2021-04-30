package com.anthonyh.afuweather.util

import com.anthonyh.afuweather.mvvm.weather.model.QueryWeatherViewModelFactory
import com.anthonyh.afuweather.mvvm.weather.repository.QueryWeatherRepository

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */

object InjectorUtil {


    private fun getQueryWeatherRepository() = QueryWeatherRepository.getInstance()

    fun getQueryWeatherViewModelFactory() =
        QueryWeatherViewModelFactory(getQueryWeatherRepository()!!)
}
