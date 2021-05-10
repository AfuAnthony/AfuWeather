package com.anthonyh.afuweather.mvvm.weather.model

import androidx.lifecycle.*
import com.anthonyh.afuweather.db.WeatherData
import com.anthonyh.afuweather.mvvm.weather.QueryParam
import com.anthonyh.afuweather.mvvm.weather.repository.QueryWeatherRepository
import com.anthonyh.afuweather.mvvm.weather.repository.Resource

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class QueryWeatherViewModel(private val queryWeatherRepository: QueryWeatherRepository) :
    ViewModel() {


    private val _queryLiveData = MutableLiveData<QueryParam>()

    val weatherLiveData: LiveData<Resource<WeatherData>> =
        _queryLiveData.switchMap {
            queryWeatherRepository.queryWeather(
                it.longitude,
                it.latitude,
                it.locationName
            )
        }


    fun queryWeather(longitude: Double, latitude: Double, locationName: String) {
        _queryLiveData.value = QueryParam(longitude, latitude, locationName)
    }

}