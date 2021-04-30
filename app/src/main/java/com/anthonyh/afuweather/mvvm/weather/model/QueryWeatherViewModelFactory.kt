package com.anthonyh.afuweather.mvvm.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anthonyh.afuweather.mvvm.weather.repository.QueryWeatherRepository

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class QueryWeatherViewModelFactory(private val queryWeatherRepository: QueryWeatherRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QueryWeatherViewModel(queryWeatherRepository) as T
    }

}