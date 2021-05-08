package com.anthonyh.afuweather.view

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonyh.afuweather.R
import com.anthonyh.afuweather.mvvm.weather.adapter.WeatherAdapter
import com.anthonyh.afuweather.mvvm.weather.network.entity.CaiYunWeather

/**
@author Anthony.H
@date: 2021/5/6
@desription:可以拉伸滑动的一个竖向的抽屉
 */
class DrawerRecyclerView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    companion object {
        private const val TAG = "DrawerRecyclerView"
    }

    private var recyclerView: RecyclerView? = null
    private var handleView: HandleView? = null
    private var weatherAdapter: WeatherAdapter = WeatherAdapter()


    fun refresh(caiYunWeather: CaiYunWeather) {
        weatherAdapter.refresh(caiYunWeather)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        var listener = ViewTreeObserver.OnGlobalLayoutListener {
            if (childCount == 0) {
                val parent = parent as FrameLayout
                handleView = HandleView(
                    context,
                    height,
                    parent.height - resources.getDimension(R.dimen.search_edit_height).toInt()
                )
                val layoutParamsHandleView = LayoutParams(
                    width / 5,
                    resources.getDimension(R.dimen.handle_height).toInt()
                )
                layoutParamsHandleView.gravity = Gravity.CENTER_HORIZONTAL
                addView(handleView, layoutParamsHandleView)
                recyclerView = RecyclerView(context)
                val layoutParamsRecyclerView =
                    LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                addView(recyclerView, layoutParamsRecyclerView)
                recyclerView?.let {
                    it.background = ContextCompat.getDrawable(context, R.drawable.bg)
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = weatherAdapter
                    it.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            LinearLayoutManager.VERTICAL
                        ).apply {
                            setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
                        })
                }
            }
            viewTreeObserver.removeOnGlobalLayoutListener { this }
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        handleView?.onParentSizeChange(h)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (handleView!!.isParentMoved()) {
            return true
        }
        return super.onTouchEvent(event)
    }

    inner class HandleView(
        context: Context?,
        private val minParentHeight: Int,
        private val maxParentHeight: Int
    ) :
        RelativeLayout(context) {

        private var handleIv: ImageView? = null
        private var lastTouchY: Float = 0f
        private var parentHeight = 0

        init {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                if (childCount == 0) {
                    handleIv = AppCompatImageView(context!!)
                    handleIv?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    val layoutParams = LayoutParams(width / 2, width / 2)
                    layoutParams.addRule(CENTER_IN_PARENT)
                    setBackgroundColor(android.graphics.Color.parseColor("#66006666"))
                    handleIv?.layoutParams = layoutParams
                    addView(handleIv)
                }
                viewTreeObserver.removeOnGlobalLayoutListener { this }
            }
            viewTreeObserver.addOnGlobalLayoutListener(listener)
        }

        //为什么在这里添加ImageView不能显示内容？
        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            super.onLayout(changed, l, t, r, b)
        }


        override fun onTouchEvent(event: MotionEvent?): Boolean {
            when (event!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowY = event.y
                    val delY = nowY - lastTouchY
                    if (delY < 0) {//上边界
                        if (parentHeight <= maxParentHeight) {
                            val parentView = parent as DrawerRecyclerView
                            val lp = parentView.layoutParams
                            lp.height -= (delY).toInt()
                            correctPos(lp)
                            parentView.layoutParams = lp
                        }
                    } else if (delY > 0) {//下边界
                        if (parentHeight >= minParentHeight) {
                            val parentView = parent as DrawerRecyclerView
                            val lp = parentView.layoutParams
                            lp.height -= (delY).toInt()
                            correctPos(lp)
                            parentView.layoutParams = lp
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (parentHeight >= maxParentHeight / 2) {
                        handleIv?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    } else {
                        handleIv?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    }

                }
            }
            return true
        }

        fun onParentSizeChange(h: Int) {
            parentHeight = h
        }

        fun isParentMoved() = parentHeight > minParentHeight


        /**
         * 防止滑动太快过了边界，纠正位置
         */
        fun correctPos(layoutParams: ViewGroup.LayoutParams) {
            if (layoutParams.height > maxParentHeight) {
                layoutParams.height = maxParentHeight
            } else if (layoutParams.height < minParentHeight) {
                layoutParams.height = minParentHeight
            }
        }

    }


}