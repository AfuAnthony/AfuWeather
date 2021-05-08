package com.anthonyh.afuweather.mvvm.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.afuweather.BR
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather
import com.anthonyh.afuweather.util.dealDateFormat

/**
@author Anthony.H
@date: 2021/5/7
@desription:
 */
class WeatherAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var caiYunWeather: CaiYunWeather? = null

    private val REALTIME_TYPE = 0x1
    private val NOTICE_TYPE = 0x2


    fun refresh(_caiYunWeather: CaiYunWeather) {
        caiYunWeather = _caiYunWeather
        notifyDataSetChanged()
    }


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
                holder.viewDataBinding.let {
                    it.setVariable(
                        BR.realTimeWeather,

                        caiYunWeather!!.result!!.realTime
                    )
                    it.setVariable(BR.locationName, caiYunWeather!!.locationName)
                    it.setVariable(BR.serverTime, caiYunWeather!!.serverTime)
                    it.setVariable(BR.forecastKeypoint, caiYunWeather!!.result!!.forecastKeypoint)
                }

            }

            is NoticeWeatherViewHolder -> {
                holder.viewDataBinding.let {
                    it.setVariable(
                        BR.noticeDate,
                        dealDateFormat(caiYunWeather!!.result!!.daily!!.skycon?.get(position - 1)!!.date!!)

                    )
                    it.setVariable(
                        BR.noticeSkyCon,
                        caiYunWeather!!.result!!.daily!!.skycon?.get(position - 1)!!.value
                    )

                    it.setVariable(
                        BR.maxTem,
                        caiYunWeather!!.result!!.daily!!.temperature?.get(position - 1)!!.max

                    )

                    it.setVariable(
                        BR.minTem,
                        caiYunWeather!!.result!!.daily!!.temperature?.get(position - 1)!!.min
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {

        return if (caiYunWeather == null) {
            0
        } else {
            caiYunWeather!!.result!!.daily!!.skycon!!.size + 1
        }
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


