package com.anthonyh.afuweather.mvp.weather

import android.util.Log
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.junit.Test
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author Anthony.H
 * @date: 2021/1/19 0019
 * @desription:
 */
class WeatherPresenterTest : TestCase() {


    @Test
    fun testGetWeather() {
        val presenter = WeatherPresenterImpl()
        presenter.requestWeather("112")
        /////
        GlobalScope.launch {
            val result: String = suspendCancellableCoroutine { a ->
                val aa = 1
                val b = 2
                println(a)
                a.resume("")
                a.cancel()
            }

            suspendCoroutine { a ->
            }

        }
    }

    @Test
    fun testCancel() {
        MainScope().launch {
            val data = fetchUserData()
            Log.e("TAG", "${Thread.currentThread().name},testCancel: $data")
        }


    }


    private suspend fun fetchUserData() = suspendCancellableCoroutine<String> {
        Thread.sleep(3000)
        it.resume("success get data${Thread.currentThread().name}")
    }


    @Test
    fun testFormat() {
        val value: Double = 1.23456789321

        Log.e("testFormat", "testFormat: ${DecimalFormat("#.000000").format(value).toDouble()}")
    }

    @Test
    fun testDateFormat() {
        val df = SimpleDateFormat("yyyy-MM-dd'T'hh:mmZ")
        val date1 = df.parse("2021-05-08T00:00+08:00")//2013-03-13T20:59:31+0000
        Log.e("testDateFormat", "testDateFormat: ${date1}")
        val df2 = SimpleDateFormat("MM月dd日")
        df2.timeZone = TimeZone.getTimeZone("GMT")

        Log.e("testDateFormat", "testDateFormat: ${df2.format(date1)}")

    }

}

