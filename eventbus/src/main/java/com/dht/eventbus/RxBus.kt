package com.dht.eventbus

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * @author Administrator
 */
class RxBus private constructor() {

    val mBus: Subject<Any> = PublishSubject.create<Any>().toSerialized()
    var hashMap: HashMap<Any, Disposable>? = null


    init {
        hashMap = HashMap()
    }

    companion object {
        val INSTANCE: RxBus = InstanceHolder.INSTANCE
    }

    private object InstanceHolder {
        internal val INSTANCE = RxBus()
    }

    fun post(event: Any) {
        mBus.onNext(event)
    }

    fun <T> toObservable(eventType: Class<T>?): Observable<T> {
        return mBus.ofType(eventType)
    }

    /**
     * 判断是否有订阅者
     */
    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }

    /**
     * 若订阅过一次则不再订阅 避免重复订阅
     *
     * @param eventType     事件类型
     * @param localCallBack 回调接口
     * @param
     */
    inline fun <reified event : Any> toRxBusResult(callBack: RxCallBack) {
        val disposable = toResult<event>(callBack) ?: return
        disposable.dispose() //取消订阅后再次调用该方法
        hashMap?.remove(event::class.java.name)
        toResult<event>(callBack)
    }

    inline fun <reified event : Any> toResult(callBack: RxCallBack): Disposable? {
        val disposable: Disposable? = hashMap?.get(event::class.java.name)
        if (disposable == null) {
            val observable = this.mBus.ofType(event::class.java)
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Any> {


                    override fun onError(e: Throwable) {
                        hashMap?.remove(event::class.java.name)
                    }

                    override fun onSubscribe(d: Disposable) {
                        hashMap?.put(event::class.java.name, d)
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: Any) {
                        callBack.onCallBack(t)
                    }
                })
            return null
        }
        return disposable
    }


}