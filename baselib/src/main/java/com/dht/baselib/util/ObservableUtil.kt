package com.dht.baselib.util

import android.annotation.SuppressLint
import android.util.Log
import com.dht.baselib.callback.ConsumerCallBack
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.callback.ObservableCallback
import com.dht.baselib.callback.ObserverCallback
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import java.util.concurrent.TimeUnit

/**
 * RxJava Observable 封装工具
 *
 *
 * created by dht on 2018/7/6 09:05
 *
 * @author Administrator
 */
class ObservableUtil {
    /**
     * 用于轮循红外测温数据
     *
     * @param localCallback 回调接口
     * @return Disposable
     */
    fun getDisposable(localCallback: LocalCallback<Timed<Long?>?>): Disposable {
        return Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .timeInterval(TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { longTimed -> localCallback.onChangeData(longTimed) }
    }

    /**
     * 异步执行方法
     *
     * @param observableCallback        被观察者回调接口
     * @param consumerCallBack          消费者回调接口
     * @param throwableConsumerCallBack 消费者回调接口
     */
    @SuppressLint("CheckResult")
    fun <T> execute(
        observableCallback: ObservableCallback<T>?,
        consumerCallBack: ConsumerCallBack<T>?,
        throwableConsumerCallBack: ConsumerCallBack<Throwable?>?
    ) {
        Observable.create(observableCallback)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumerCallBack, throwableConsumerCallBack)
    }

    companion object {
        private const val TAG = "ObservableUtil"
        private val instance: ObservableUtil? = null
        const val KEY_SUCCESSFUL = "successful"
        //    public static <T> void flatMap(){
//        Observable.create()
//    }
        /**
         * 异步执行方法
         *
         * @param observableCallback 被观察者回调接口
         * @param localCallback      回调接口
         */
        fun <T> execute(
            observableCallback: ObservableCallback<T>?,
            localCallback: LocalCallback<T?>?
        ) {
            Observable.create(observableCallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber(localCallback))
        }

        /**
         * 返回并获取数据结果
         *
         * @param localCallback NetworkCallBack
         * @param <T>           T
         * @return Observer
        </T> */
        fun <T> subscriber(localCallback: LocalCallback<T?>?): Observer<T> {
            return object : ObserverCallback<T>() {
                override fun onNext(value: T) {
                    super.onNext(value)
                    if (localCallback == null) {
                        return
                    }
                    localCallback.onChangeData(value)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    Log.e(TAG, " onError: e = ", e)
                    if (localCallback == null) {
                        return
                    }
                    localCallback.onChangeData(null)
                }
            }
        }
    }
}