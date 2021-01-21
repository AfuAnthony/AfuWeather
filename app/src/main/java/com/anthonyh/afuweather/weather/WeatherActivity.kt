package com.anthonyh.afuweather.weather

import com.anthonyh.afuweather.base.BaseActivity
import com.anthonyh.afuweather.databinding.ActivityMainBinding
import com.anthonyh.afuweather.weather.entity.HeWeather
import java.lang.Exception

class WeatherActivity :
    BaseActivity<WeatherContract.BaseWeatherPresenter, WeatherContract.IWeatherView, ActivityMainBinding>(),
    WeatherContract.IWeatherView {
    override fun createPresenter(): WeatherPresenterImpl {
        return WeatherPresenterImpl()
    }

    override fun requestWeather(cityId: String) {
        return presenter?.requestWeather(cityId)
    }

    override fun onWeatherResultCallBack(result: HeWeather?, e: Exception?) {
    }

    override fun onCreateInit() {
        requestWeather("CN101010100")
    }

}