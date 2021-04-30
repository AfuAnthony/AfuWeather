package com.anthonyh.afuweather.mvp.weather

import android.util.Log
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.junit.Test
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


}