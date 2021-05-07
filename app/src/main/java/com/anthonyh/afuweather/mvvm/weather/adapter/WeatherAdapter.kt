package com.anthonyh.afuweather.mvvm.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.afuweather.BR
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather

/**
@author Anthony.H
@date: 2021/5/7
@desription:
 */
class WeatherAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val caiYunWeather: CaiYunWeather? = null

    private val REALTIME_TYPE = 0x1
    private val NOTICE_TYPE = 0x2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            REALTIME_TYPE -> {
                val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_weather_main,
                    parent,
                    false
                )
                return MainWeatherViewHolder(viewDataBinding)
            }
            else -> {
                val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_weather_notice,
                    parent,
                    false
                )
                return NoticeWeatherViewHolder(viewDataBinding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainWeatherViewHolder -> {
                holder.viewDataBinding.setVariable(
                    BR.realTimeWeather,
                    caiYunWeather!!.result!!.daily!!.skycon
                )

                holder.viewDataBinding.setVariable(BR.locationName, caiYunWeather.locationName)
            }

            is NoticeWeatherViewHolder-> {
//                 holder.viewDataBinding.setVariable(BR.)
            }
        }
    }

    override fun getItemCount(): Int {
        return caiYunWeather!!.result!!.daily!!.skycon!!.size + 1
    }


    override fun getItemViewType(position: Int): Int {


        return when (position) {
            0 -> {//显示当前天气信息
                REALTIME_TYPE
            }
            else -> {//显示天气预报
                NOTICE_TYPE
            }
        }
    }

}


