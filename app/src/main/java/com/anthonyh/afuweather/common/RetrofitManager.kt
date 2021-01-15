package com.anthonyh.afuweather.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASEURL="http://guolin.tech"

object  RetrofitManager{


    private val  retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    fun <T> createService( clazz:Class<T>):T{
        return retrofit.create(clazz)
    }

}
