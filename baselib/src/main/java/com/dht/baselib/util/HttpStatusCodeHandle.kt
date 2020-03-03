package com.dht.baselib.util

import android.content.Context
import com.dht.baselib.R


/**
 * 会话超时
 */
fun Context.onSessionTimeout() {
    toast(R.string.login_info_due)
}

/**
 * 数据获取失败
 */
fun Context.onServiceFailure(){
    toast(R.string.obtain_data_failure)
}

/**
 * 服务器异常
 */
fun Context.onServiceException(){
    toast(R.string.service_exception)
}


