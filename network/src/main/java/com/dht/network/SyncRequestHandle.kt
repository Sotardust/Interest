//package com.dht.network
//
//import android.util.Log
//import io.reactivex.ObservableEmitter
//import kotlinx.coroutines.CoroutineExceptionHandler
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import retrofit2.Call
//
///**
// *  同步请求处理
// *
// *  created by dht on 2020/1/9 17:37
// */
//
///**
// * 抽出异步部操作处理方法
// *
// * @param call            Call
// * @param networkCallback 回调接口
// * @param field           方法名
// * @param TAG             TAG
// */
//fun <T> ansyOperationHandle(
//    call: Call<T>,
//    networkCallback: NetworkCallback<T>?,
//    field: String,
//    TAG: String?
//) {
//    val handler =CoroutineExceptionHandler()
//    GlobalScope.launch {
//
//        val response = call.execute()
//    }
//    ansyObtainData(object : ObservableCallback<T>() {
//        @Throws(Exception::class)
//        override fun subscribe(emitter: ObservableEmitter<T>) {
//            super.subscribe(emitter)
//
//            if (call.request().url().toString().contains("login")) {
//                val cookie = response.headers()["Set-Cookie"]
//                Log.d(TAG, "subscribe: cookie = $cookie")
//                MessagePreferences.INSTANCE.cookie = cookie
//            }
//            emitter.onNext(response.body()!!)
//        }
//    }, object : ObserverCallback<T>() {
//        override fun onNext(t: T) {
//            super.onNext(t)
//            if (networkCallback == null) {
//                return
//            }
//            networkCallback.onChangeData(t)
//        }
//
//        override fun onError(e: Throwable) {
//            super.onError(e)
//            Log.e(TAG, "$field onError: e = ", e)
//            if (networkCallback == null) {
//                return
//            }
//            networkCallback.onChangeData(null)
//        }
//    })
//}