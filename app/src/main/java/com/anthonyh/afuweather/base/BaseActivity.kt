package com.anthonyh.afuweather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
open abstract class BaseActivity<P : BasePresenter<V>, V : BaseView> : AppCompatActivity(),
    BaseView {

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.attachView(this as V)
        lifecycle.addObserver(createPresenter())
    }


    abstract fun createPresenter(): P


    override fun onLoading() {
    }

    override fun onDisLoading() {
    }

}