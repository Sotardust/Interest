package com.dht.baselib.callback

interface Callback<T> {
    fun onChangeData(data: T?)
    fun onChangeData()
}