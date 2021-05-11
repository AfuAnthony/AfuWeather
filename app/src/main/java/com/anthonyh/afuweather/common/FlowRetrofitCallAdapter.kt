package com.anthonyh.afuweather.common

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Type


internal class FlowRecotriftBodyCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, Flow<T>> {
    override fun adapt(call: Call<T>): Flow<T> {
        return flow {
            emit(
                suspendCancellableCoroutine { continuation ->
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            try {
                                continuation.resume(response.body()!!)
                            } catch (e: Exception) {
                                continuation.resumeWithException(e)
                            }
                        }
                    })
                    continuation.invokeOnCancellation { call.cancel() }
                }
            )
        }
    }

    override fun responseType() = responseType
}