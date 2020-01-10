package com.dht.baselib.callback

/**
 * 基础回调接口
 *
 *
 * created by dht on 2018/7/5 18:35
 *
 * @author Administrator
 */
open class LocalCallback<T> : Callback<T> {
    override fun onChangeData(data: T?) {}
    override fun onChangeData() {}
}