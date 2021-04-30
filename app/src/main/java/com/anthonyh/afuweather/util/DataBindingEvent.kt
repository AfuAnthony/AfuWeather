package com.anthonyh.afuweather.util

import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
@author Anthony.H
@date: 2021/4/29
@desription:
 */
@BindingMethods(
    value = [
        BindingMethod(
            type = AutoCompleteTextView::class,
            attribute = "android:add",
            method = "setImageTintList"
        )]
)
class DataBindingEvent {
}