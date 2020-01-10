package com.dht.interest.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * created by Administrator on 2018/12/13 18:28
 *
 * @author Administrator
 */
object ScreenUtil {

    @JvmStatic
    fun install(context: Context) {
        val wm = (context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        width = point.x
        height = point.y
    }

    var width = 0
    var height = 0
}