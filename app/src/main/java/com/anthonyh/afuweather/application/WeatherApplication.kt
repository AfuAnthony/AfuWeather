package com.anthonyh.afuweather.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}