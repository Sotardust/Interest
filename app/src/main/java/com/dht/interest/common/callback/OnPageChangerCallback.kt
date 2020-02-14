package com.dht.interest.common.callback

import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * Created by dai on 2018/3/20.
 */
open class OnPageChangerCallback : OnPageChangeListener {
    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}