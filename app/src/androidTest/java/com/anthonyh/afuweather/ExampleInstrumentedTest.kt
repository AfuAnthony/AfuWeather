package com.anthonyh.afuweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.IOException
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.anthonyh.afuweather", appContext.packageName)
    }

    @Test
    fun test1() {
        println("cdscdscdscdsc")
        try {
            main()
        } catch (e: Exception) {
            println("1111--------------" + e.message)
        }

    }

    fun main() = runBlocking {
        println("first line------------")
        try {
            val job = GlobalScope.launch {
                println("Throwing exception from launch")
                try {
                    throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
                } catch (e: Exception) {
                    println("33333333------" + e.message)
                }
            }
            try {
                job.join()
            } catch (e: Exception) {
                println("44444444444--------" + e.message)
            }

            println("Joined failed job")
            val deferred = GlobalScope.async {
                println("Throwing exception from async")
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            println("last line------------")
            try {
                deferred.await()
                println("Unreached")
            } catch (e: ArithmeticException) {
                println("5555555555-------" + e.toString())
            }
        } catch (e: Exception) {
            println("222222--------------" + e.message)
        }
    }

    @Test
    fun testCorException2() {
        runBlocking {
            //sampleStart
            val handler = CoroutineExceptionHandler { _, exception ->
                println("Caught $exception")
            }

            val job = GlobalScope.launch(handler) {
                throw AssertionError()
            }
            val deferred = GlobalScope.async(handler) {
                throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
            }
            joinAll(job, deferred)
            //sampleEnd
        }
    }

    @Test
    fun testCorException3() {
        runBlocking {
            //sampleStart
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("Child is cancelled")
                    }
                }
                yield()
                println("Cancelling child")
                child.cancel()
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()
            //sampleEnd
        }
    }

    @Test
    fun testCorException4() {
        runBlocking {
//sampleStart
            val handler = CoroutineExceptionHandler { _, exception ->
                println("Caught $exception")
            }
            val job = GlobalScope.launch(handler) {
                launch { // the first child
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        withContext(NonCancellable) {
                            println("Children are cancelled, but exception is not handled until all children terminate")
                            delay(100)
                            println("The first child finished its non cancellable block")
                        }
                    }
                }
                launch { // the second child
                    delay(10)
                    println("Second child throws an exception")
                    throw ArithmeticException()
                }
            }
            job.join()
//sampleEnd
        }
    }


    @Test
    fun testCorFinally() {
        val job = Job()
        CoroutineScope(job).launch {
            try {
                println("start--testCorFinally")
                throw IOException("cdscdscds")
                delay(3000)
                println("end--testCorFinally")
            } catch (e: Exception) {
            } finally {
                println("finally---------------------")
            }
        }
        job.cancel()
    }


}

@Test
fun testViewModel() {
}


