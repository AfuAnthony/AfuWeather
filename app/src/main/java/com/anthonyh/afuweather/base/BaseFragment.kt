package com.anthonyh.afuweather.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */

open abstract class BaseFragment<P : BasePresenter<V>, V : BaseView> : Fragment(), BaseView {

    protected var presenter: P? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter?.attachView(this as V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


}