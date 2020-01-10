package com.dht.baselib.base

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Administrator on 2018/5/24 0024.
 */
class BaseViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val result = abs(ev.x - 1080f).toInt()
        return result >= 50 && super.onInterceptTouchEvent(ev)
    }

    companion object {
        private const val TAG = "MyViewPager"
    }
}