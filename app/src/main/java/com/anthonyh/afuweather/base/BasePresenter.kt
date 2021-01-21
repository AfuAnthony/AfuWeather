package com.anthonyh.afuweather.base

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
open abstract class BasePresenter<VIEW : BaseView> : LifecycleEventObserver {

    protected var view: VIEW? = null
    protected lateinit var job: Job
    protected var lifecycleState: Lifecycle.State = Lifecycle.State.DESTROYED
    fun attachView(v: VIEW) {
        view = v
        if (needJob()) {
            job = Job()
        }
    }

    protected fun isNeedCallBack(): Boolean {
        return lifecycleState != Lifecycle.State.DESTROYED
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.e("BasePresenter", "onStateChanged: ${source.lifecycle.currentState},${Thread.currentThread().name}")
        this.lifecycleState = source.lifecycle.currentState
        if (lifecycleState == Lifecycle.State.DESTROYED) {
            this.view = null
            if (this::job.isInitialized) {
                job.cancel()
            }
        }
    }


    protected fun needJob(): Boolean {
        return true
    }

    protected suspend fun showLoading() {
        withContext(Dispatchers.Main) {
            view?.onLoading()
        }
    }

    protected suspend fun disLoading() {
        withContext(Dispatchers.Main) {
            view?.onDisLoading()
        }
    }

}