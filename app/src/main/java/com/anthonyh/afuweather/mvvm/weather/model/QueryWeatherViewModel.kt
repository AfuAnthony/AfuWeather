package com.anthonyh.afuweather.mvvm.weather.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonyh.afuweather.error.WeatherException
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import com.anthonyh.afuweather.mvvm.weather.repository.QueryWeatherRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class QueryWeatherViewModel(val queryWeatherRepository: QueryWeatherRepository) : ViewModel() {


    private var _weatherLiveData = MutableLiveData<CaiYunWeather>()

    val weatherLiveData: LiveData<CaiYunWeather> = _weatherLiveData

    private var _exceptionLiveData = MutableLiveData<WeatherException>()
    val exceptionLiveData: LiveData<WeatherException> = _exceptionLiveData


    fun queryWeather(longitude: Double, latitude: Double, locationName: String) {
        viewModelScope.launch {
            try {
                _weatherLiveData.value =
                    queryWeatherRepository.queryWeather(longitude, latitude, locationName)
            } catch (e: Exception) {
                _exceptionLiveData.value = WeatherException(null, e.message)
            }
        }

    }

}