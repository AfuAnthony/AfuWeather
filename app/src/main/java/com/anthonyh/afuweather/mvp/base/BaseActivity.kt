package com.anthonyh.afuweather.mvp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.anthonyh.afuweather.util.ViewBindingUtil

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
open abstract class BaseActivity<P : BasePresenter<V>, V : BaseView, VB : ViewBinding> :
    AppCompatActivity(),
    BaseView {

    protected lateinit var presenter: P
    protected lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ViewBindingUtil.create(this::class.java, layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
        presenter?.attachView(this as V)
        onCreateInit()
    }

    abstract fun onCreateInit()

    abstract fun createPresenter(): P


    override fun onLoading() {
    }

    override fun onDisLoading() {
    }

}