package com.anthonyh.afuweather.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Job

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
open abstract class BasePresenter<VIEW : BaseView> : LifecycleObserver {

    protected var view: VIEW? = null
    protected lateinit var job: Job

    fun attachView(v: VIEW) {
        view = v
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (needJob()) {
            job = Job()
        }
    }

    protected fun needJob(): Boolean {
        return true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.view = null
    }


}