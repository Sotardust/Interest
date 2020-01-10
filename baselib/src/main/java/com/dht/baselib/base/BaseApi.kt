package com.dht.baselib.base

import com.dht.baselib.callback.ObservableCallback
import com.dht.baselib.callback.ObserverCallback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * created by dht on 2018/12/24 10:53
 *
 * @author Administrator
 */
class BaseApi {
    /**
     * 异步获取数据函数
     *
     * @param observableCallback ObservableCallback
     * @param observerCallback   ObserverCallback
     */
    fun <T> ansyObtainData(
        observableCallback: ObservableCallback<T>,
        observerCallback: ObserverCallback<T>
    ) {
        Observable.create(observableCallback)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerCallback)
    }



    companion object {
        // 使用本机iP地址 不能使用 127.0.0.1（虚拟机把其作为自身IP）
        const val BASE_URL = "http://192.168.1.71:8080/message/"
    }
}