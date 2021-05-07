package com.anthonyh.afuweather.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitManager {

    const val GUOLINBASEURL = "http://guolin.tech"
    const val CAIYUNBASEURL = "https://api.caiyunapp.com"
    const val CAIYUN = "caiyun"
    const val GUOLIN = "guolin"


    private val guolinRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(GUOLINBASEURL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createService(retrofit: Retrofit, clazz: Class<T>): T {
        return retrofit.create(clazz)
    }


    private val caiyunRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(CAIYUNBASEURL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()


    fun getRetrofit(type: String): Retrofit? {
        when (type) {
            CAIYUN -> {
                return caiyunRetrofit
            }
            GUOLIN -> {
                return guolinRetrofit
            }
        }
        return null
    }

}
