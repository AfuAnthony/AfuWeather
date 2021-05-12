package com.anthonyh.afuweather.mvvm.weather.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import com.anthonyh.afuweather.common.*
import kotlinx.coroutines.flow.*
import java.lang.Exception


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
 * @param resultInterceptor 在网络数据转成数据库存储数据前，可能需要做一些处理
 * 简陋版[Flow]实现，没有用多个[Flow]
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
    private val resultInterceptor: ((result: RequestType) -> RequestType)? = null,
) {

    companion object {
        private const val TAG = "NetworkBoundResource"
    }


    private val flow = flow {
        emit(Resource.loading(null))

        val dbResult = loadFromDb()
        if (shouldFetch(dbResult)) {
            var netResult: RequestType
            try {
                netResult = createCall()
                resultInterceptor?.let {
                    netResult = it.invoke(netResult)
                }
                saveCallResult(netResult)
                val newDbSource = loadFromDb()
                emit(Resource.success(newDbSource))
            } catch (e: Exception) {
                emit(Resource.error(e.message, dbResult))
            }
        } else {
            emit(Resource.success(dbResult))
        }

    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = flow.asLiveData()

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): RequestType
}