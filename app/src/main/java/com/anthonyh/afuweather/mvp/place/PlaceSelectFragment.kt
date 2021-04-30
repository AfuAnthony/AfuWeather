package com.anthonyh.afuweather.mvp.place

import com.anthonyh.afuweather.mvp.base.BaseFragment
import com.anthonyh.afuweather.databinding.ChooseAreaBinding

/**
@author Anthony.H
@date: 2021/1/15 0015
@desription:
 */
class PlaceSelectFragment :
    BaseFragment<PlaceContract.BasePlacePresenter, PlaceContract.IPlaceView, ChooseAreaBinding>() {


    override fun createPresenter(): PlaceContract.BasePlacePresenter {
        return PlacePresenterImpl()
    }


}