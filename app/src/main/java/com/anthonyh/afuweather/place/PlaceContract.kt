package com.anthonyh.afuweather.place

import com.anthonyh.afuweather.base.BasePresenter
import com.anthonyh.afuweather.base.BaseView

/**
@author Anthony.H
@date: 2021/1/20 0020
@desription:
 */
interface PlaceContract {

    interface IPlaceView : BaseView {

    }

    abstract class BasePlacePresenter : BasePresenter<IPlaceView>() {

    }

    interface IPlaceModel {}

}