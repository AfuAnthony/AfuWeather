package com.anthonyh.afuweather.common

import android.util.Log
import androidx.lifecycle.LiveData
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
import java.util.concurrent.atomic.AtomicBoolean


internal class FlowRecotriftBodyCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, Flow<ApiResponse<T>>> {

    companion object {
        private const val TAG = "FlowRetrofitCallAdapter"
    }

    override fun adapt(call: Call<T>): Flow<ApiResponse<T>> {
        return flow {
            emit(
                try {
                    suspendCancellableCoroutine { continuation ->
                        call.enqueue(object : Callback<T> {
                            override fun onFailure(call: Call<T>, t: Throwable) {
                                continuation.resumeWithException(t)
                            }

                            override fun onResponse(call: Call<T>, response: Response<T>) {
                                try {
                                    continuation.resume(ApiResponse.create(response))
                                } catch (e: Exception) {
                                    continuation.resumeWithException(Throwable(e))
                                }
                            }
                        })
                        continuation.invokeOnCancellation { call.cancel() }
                    }
                } catch (e: java.lang.Exception) {
                    Log.d(TAG, "adapt,catch error:${e.message} ")
                    ApiResponse.create<T>(Throwable(e))
                }
            )
        }
    }

    override fun responseType() = responseType

}

//internal class ApiResponseCallAdapter<T>(private val responseType: Type) :
//    CallAdapter<T, ApiResponse<T>> {
//    companion object {
//        private const val TAG = "FlowRetrofitCallAdapter"
//    }
//
//    override fun responseType(): Type = responseType
//
//    override fun adapt(call: Call<T>): ApiResponse<T> {
//
//        try {
//            suspendCancellableCoroutine { continuation ->
//                call.enqueue(object : Callback<T> {
//                    override fun onFailure(call: Call<T>, t: Throwable) {
//                        continuation.resumeWithException(t)
//                    }
//
//                    override fun onResponse(call: Call<T>, response: Response<T>) {
//                        try {
//                            continuation.resume(ApiResponse.create(response))
//                        } catch (e: Exception) {
//                            continuation.resumeWithException(Throwable(e))
//                        }
//                    }
//                })
//                continuation.invokeOnCancellation { call.cancel() }
//            }
//        } catch (e: java.lang.Exception) {
//            Log.d(TAG, "adapt,catch error:${e.message} ")
//            ApiResponse.create<T>(Throwable(e))
//        }
//    }
//
//}


