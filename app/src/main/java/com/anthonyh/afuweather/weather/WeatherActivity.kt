package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.base.BaseActivity
import com.anthonyh.afuweather.weather.entity.HeWeather

class WeatherActivity :
    BaseActivity<WeatherContract.BaseWeatherPresenter, WeatherContract.WeatherView>(),
    WeatherContract.WeatherView {

    override fun createPresenter(): WeatherPresenter {
        return WeatherPresenter()
    }

    override fun requestWeather(): HeWeather? {
        return presenter?.requestWeather()
    }

    override fun getView(): WeatherContract.WeatherView {
        return this
    }


}