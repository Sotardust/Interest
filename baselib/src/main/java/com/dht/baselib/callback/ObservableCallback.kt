package com.dht.baselib.callback

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

/**
 * Created by Administrator on 2018/4/1 0001.
 */
open class ObservableCallback<T> : ObservableOnSubscribe<T> {
    @Throws(Exception::class)
    override fun subscribe(emitter: ObservableEmitter<T>) {
    }
}