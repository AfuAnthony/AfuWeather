package com.anthonyh.afuweather.util

import android.text.TextWatcher
import android.widget.*
import androidx.databinding.BindingAdapter
import com.anthonyh.afuweather.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.title.view.*

/**
@author Anthony.H
@date: 2021/4/27
@desription:
 */

@BindingAdapter("app:imageLoad")
fun imageLoad(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url) to imageView
}

@BindingAdapter("app:imageLoadExtension")
fun ImageView.loadurl(url: String?) {
    Glide.with(context).load(url) to this
}

@BindingAdapter("app:bindTextWatcher")
fun EditText.bindTextWatcher(textWatcher: TextWatcher?) {
    addTextChangedListener(textWatcher)
}

@BindingAdapter("bindItemClick")
fun AutoCompleteTextView.bindItemClick(onItemClickListener: AdapterView.OnItemClickListener) {

    setOnItemClickListener(onItemClickListener)
}


/**
 *
天气现象	代码	备注
晴（白天）	CLEAR_DAY	cloudrate < 0.2
晴（夜间）	CLEAR_NIGHT	cloudrate < 0.2
多云（白天）	PARTLY_CLOUDY_DAY	0.8 >= cloudrate > 0.2
多云（夜间）	PARTLY_CLOUDY_NIGHT	0.8 >= cloudrate > 0.2
阴	CLOUDY	cloudrate > 0.8
轻度雾霾	LIGHT_HAZE	PM2.5 100~150
中度雾霾	MODERATE_HAZE	PM2.5 150~200
重度雾霾	HEAVY_HAZE	PM2.5 > 200
小雨	LIGHT_RAIN
中雨	MODERATE_RAIN
大雨	HEAVY_RAIN
暴雨	STORM_RAIN
雾	FOG	能见度低，湿度高，风速低，温度低
小雪	LIGHT_SNOW
中雪	MODERATE_SNOW
大雪	HEAVY_SNOW
暴雪	STORM_SNOW
浮尘	DUST	aqi > 150，pm10 > 150，湿度 < 30%，风速 < 6 m/s
沙尘	SAND	aqi > 150，pm10 > 150，湿度 < 30%，风速 > 6 m/s
大风	WIND
 */
@BindingAdapter("bindWeatherSkyCon")
fun TextView.bindWeatherSkyCon(skyCon: String?) {

    skyCon?.let {
        text = when (skyCon) {
            "CLEAR_DAY", "CLEAR_NIGHT" -> {
                "晴天"
            }
            "PARTLY_CLOUDY_DAY", "PARTLY_CLOUDY_NIGHT" -> {
                "多云"
            }
            "CLOUDY" -> {

                "阴"
            }
            "LIGHT_RAIN" -> {
                "小雨"
            }
            "MODERATE_RAIN" -> {
                "中雨"
            }
            "HEAVY_RAIN" -> {
                "大雨"
            }
            "STORM_RAIN" -> {
                "暴雨"
            }
            "FOG" -> {
                "雾"
            }
            "LIGHT_SNOW" -> {
                "小雪"
            }
            else -> {
                "晴天"
            }
        }
    }
}

@BindingAdapter("bindWeatherIcon")
fun ImageView.bindWeatherIcon(skyCon: String?) {
    setImageResource(R.drawable.ic_baseline_wb_sunny_24)
}

@BindingAdapter("bindTimeStamp")
fun TextView.bindTimeStamp(timeSec: String?) {

    timeSec?.let {
        text = "上次更新时间：${timeStamp2Date(timeSec, null)}"
    }

}