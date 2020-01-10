package com.tocel.patrol.util

import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce

/**
 *  定时更新工具
 *
 *  created by dht on 2019/12/23 13:53
 */

/**
 * 定时每秒更新一次轮次倒计时
 */
@ExperimentalCoroutinesApi
private fun CoroutineScope.updateTime(endTime: Long, currentTime: Long) = produce {
    var value = (endTime - currentTime).div(1000).toInt()
    while (true) {
        send(value--)
        delay(1000)
    }
}

/**
 * 秒数转换成 -day-h-min-s 格式
 */
private fun secToTime(value: Int): String? {
    var seconds = value
    val day = seconds.div(24 * 3600)
    seconds -= day.times(24 * 3600)
    val hour = seconds.div(3600)
    seconds -= hour.times(3600)
    val minute = seconds.div(60)
    seconds -= minute.times(60)
    return buildString {
        append("${if (day > 0) "$day day" else ""} ")
        append("${if (hour > 0) "$hour h" else ""} ")
        append("${if (minute > 0) "$minute min" else ""} ")
        append("${if (seconds > 0) "$seconds s" else ""} ")
    }
}

/**
 * 更新本轮次任务结束倒计时
 */
fun getJob(view: TextView, endTime: Long) = GlobalScope.launch {
    val currentTime = System.currentTimeMillis()
    val time = updateTime(endTime, currentTime)
    while (true) {
        withContext(Dispatchers.Main) {
            val value = time.receive()
            view.text = if (value > 0) "距离本轮任务结束还有：${secToTime(value)} " else "本轮次已结束请退出后重新登录 "
        }
    }
}