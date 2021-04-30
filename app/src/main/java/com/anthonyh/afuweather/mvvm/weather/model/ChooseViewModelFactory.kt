package com.anthonyh.afuweather.mvvm.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
class ChooseViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ChoosePlaceViewModel() as T
    }
}