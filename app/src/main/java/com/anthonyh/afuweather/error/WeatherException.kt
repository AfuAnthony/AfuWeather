package com.anthonyh.afuweather.error

import java.lang.Exception

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class WeatherException(var code: Int?, msg: String?) : Exception(msg) {

}