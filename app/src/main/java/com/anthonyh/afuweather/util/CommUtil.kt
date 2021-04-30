package com.anthonyh.afuweather.util

import android.content.Context

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
fun Context.dp2px(px: Float): Float = px / resources.displayMetrics.density + 0.5f