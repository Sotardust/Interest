package com.dht.baselib.callback

import io.reactivex.functions.Consumer

/**
 * 消费者回调函数
 *
 *
 * created by dht on 2018/8/3 21:18
 */
class ConsumerCallBack<T> : Consumer<T> {
    @Throws(Exception::class)
    override fun accept(t: T) {
    }
}