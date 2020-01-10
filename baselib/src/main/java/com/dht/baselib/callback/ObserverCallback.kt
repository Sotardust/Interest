package com.dht.baselib.callback

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by dai on 2018/3/20.
 */
open class ObserverCallback<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {}
    override fun onNext(t: T) {}
    override fun onError(e: Throwable) {}
    override fun onComplete() {}
}