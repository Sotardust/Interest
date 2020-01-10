package com.dht.baselib.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import java.util.*

/**
 * 1、避免同样的信息多次触发重复弹出的问题
 * 2、自定义弹出时间
 *
 * created by dht on 2019/12/6 17:26
 */

//第一次开始时间
var lastTime = 0L

//两次点击时间间隔为500毫秒
const val interval = 500

fun Context.toast(msg: String) {
    toast(msg, Toast.LENGTH_SHORT)
}

fun Context.toast(resId: Int) {
    toast(resId, Toast.LENGTH_SHORT)
}

fun Context.toast(msg: String, duration: Int) {
    if (timeInterval()) {
        Toast.makeText(this, msg, duration).show()
    }
}

fun Context.toast(resId: Int, duration: Int) {
    if (timeInterval()) {
        Toast.makeText(this, resId, duration).show()
    }
}

fun Context.toastCustomTime(msg: String) {
    toastCustomTime(msg, 500)
}

fun Context.toastCustomTime(resId: Int) {
    toastCustomTime(resId, 500)
}

/**
 * 判断两次点击事件是否大于时间间隔 ，是则返回true 否则返回false
 */
fun timeInterval(): Boolean {
    if (System.currentTimeMillis() - lastTime < interval) {
        return false
    }
    lastTime = System.currentTimeMillis()
    return true
}

/**
 * 自定义弹出时间
 */
fun Context.toastCustomTime(msg: String, duration: Long) {
    if (!timeInterval()) return
    val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
    toast.show()
    toastCancel(toast, duration)
}

fun Context.toastCustomTime(resId: Int, duration: Long) {
    if (!timeInterval()) return
    val toast = Toast.makeText(this, resId, Toast.LENGTH_LONG)
    toast.show()
    toastCancel(toast, duration)
}

/**
 * 自定义取消toast显示
 */
private fun toastCancel(toast: Toast, duration: Long) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            toast.cancel()
        }
    }, duration)
}

