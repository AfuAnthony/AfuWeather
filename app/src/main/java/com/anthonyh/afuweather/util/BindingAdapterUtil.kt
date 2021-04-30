package com.anthonyh.afuweather.util

import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
@author Anthony.H
@date: 2021/4/27
@desription:
 */

@BindingAdapter("app:imageLoad")
fun imageLoad(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url) to imageView
}

@BindingAdapter("app:imageLoadExtension")
fun ImageView.loadurl(url: String?) {
    Glide.with(context).load(url) to this
}

@BindingAdapter("app:bindTextWatcher")
fun EditText.bindTextWatcher(textWatcher: TextWatcher?) {
    addTextChangedListener(textWatcher)
}

@BindingAdapter("bindItemClick")
fun AutoCompleteTextView.bindItemClick(onItemClickListener: AdapterView.OnItemClickListener) {

    setOnItemClickListener(onItemClickListener)
}