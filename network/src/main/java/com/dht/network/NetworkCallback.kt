package com.dht.network

/**
 * created by dht on 2018/12/24 13:50
 */
interface NetworkCallback<T> {
    fun onChangeData(data: T?)
}