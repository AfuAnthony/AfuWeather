package com.anthonyh.afuweather.util

import com.google.gson.Gson

/**
@author Anthony.H
@date: 2021/5/7
@desription:
 */
object GsonUtil {

    private val gson = Gson()


    fun <T> toJson(obj: T) = gson.toJson(obj)

    fun <T> fromJson(json: String, clazz: Class<T>) = gson.fromJson(json, clazz)
}