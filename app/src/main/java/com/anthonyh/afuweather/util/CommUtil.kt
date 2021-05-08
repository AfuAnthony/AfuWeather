package com.anthonyh.afuweather.util

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.getSystemService
import java.lang.Long
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */

fun Context.px2dip(px: Float): Float = px / resources.displayMetrics.density + 0.5f

fun Context.px2dip(px: Int): Int = px2dip(px.toFloat()).toInt()

fun Context.dip2px(dp: Float): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    resources.displayMetrics
) //dp * resources.displayMetrics.density + 0.5f

fun Context.dip2px(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
).toInt() //dip2px(dp.toFloat()).toInt()

fun Context.sp2px(sp: Float): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp,
    resources.displayMetrics
) //sp * resources.displayMetrics.density

fun Context.sp2px(sp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp.toFloat(),
    resources.displayMetrics
).toInt() // sp2px(sp.toFloat()).toInt()

/**
 * 获取App的显示区域
 *
 * @ return point.x:App显示的宽度 point.y:App显示的高度
 *
 * @see screenSize
 */
val Context.appDisplaySize: Point
    get() {
        val p = Point()
        val wm = windowManager ?: return p
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = wm.currentWindowMetrics
            val windowInsets = metrics.windowInsets
            val insets =
                windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom
            val bounds = metrics.bounds
            p.x = bounds.width() - insetsWidth
            p.y = bounds.height() - insetsHeight
        } else {
            @Suppress("DEPRECATION")
            wm.defaultDisplay?.getSize(p)
        }
        return p
    }


/**
 * 获取屏幕的显示区域
 *
 * @return point.x:屏幕宽度 point.y:屏幕高度
 *
 * @see appDisplaySize
 */
val Context.screenSize: Point
    get() {
        val p = Point()
        val wm = windowManager ?: return p
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = wm.currentWindowMetrics.bounds
            p.x = bounds.width()
            p.y = bounds.height()
        } else {
            @Suppress("DEPRECATION")
            wm.defaultDisplay?.getRealSize(p)
        }
        return p

    }


val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = runCatching { resources.getDimensionPixelSize(resourceId) }.getOrDefault(0)
        }

        if (result == 0) {
            result = dip2px(24)
        }

        return result
    }


val Context.windowManager get() = getSystemService<WindowManager>()


const val SixFormatString = "#.000000"


fun Double.format(formatString: String) = DecimalFormat(formatString).format(this).toDouble()

fun timeStamp2Date(seconds: String?, format: String?): String? {
    var format = format
    if (seconds == null || seconds.isEmpty() || seconds == "null") {
        return ""
    }
    if (format == null || format.isEmpty()) {
        format = "yyyy-MM-dd HH:mm:ss"
    }
    val sdf = SimpleDateFormat(format)
    return sdf.format(Date(Long.valueOf(seconds + "000")))
}

fun dealDateFormat(oldDateStr: String?): String? {
    val df1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mmZ")
    val date = df1.parse(oldDateStr)
    val df2 = SimpleDateFormat("MM月dd日")
//    df2.timeZone = TimeZone.getTimeZone("GMT-16:00")
    return df2.format(date)
}
