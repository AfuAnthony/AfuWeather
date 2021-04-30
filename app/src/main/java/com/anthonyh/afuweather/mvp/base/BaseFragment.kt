package com.anthonyh.afuweather.mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.anthonyh.afuweather.util.ViewBindingUtil

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */

open abstract class BaseFragment<P : BasePresenter<V>, V : BaseView, VB : ViewBinding> : Fragment(),
    BaseView {

    protected lateinit var presenter: P
    protected var viewBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = createPresenter()
        presenter?.attachView(this as V)
        viewBinding = ViewBindingUtil.create(this::class.java, layoutInflater, container, false)
        val view = viewBinding?.root
        lifecycle.addObserver(presenter)
        presenter?.attachView(this as V)
        return view
    }

    abstract fun createPresenter(): P


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onLoading() {
    }

    override fun onDisLoading() {
    }

}