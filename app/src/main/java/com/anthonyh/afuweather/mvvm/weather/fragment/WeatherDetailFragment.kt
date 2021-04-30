package com.anthonyh.afuweather.mvvm.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.databinding.FragmentWeatherDetailBindingImpl

/**
@author Anthony.H
@date: 2021/4/28
@desription:
 */
class WeatherDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_detail, container, false)
        val bindingImp = DataBindingUtil.bind<FragmentWeatherDetailBindingImpl>(view)
        return view
    }
}