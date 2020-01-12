package com.dht.interest.login

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import com.dht.baselib.util.file.FileManager.Companion.instance
import com.dht.baselib.util.file.FileUtil
import com.dht.baselib.util.onServiceFailure
import com.dht.baselib.util.toast
import com.dht.baselib.util.toastCustomTime
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.FileOutputStream
import java.io.IOException

class LoginViewModel(@NonNull  application: Application) :
    AndroidViewModel(application) {
    private val loginApi: LoginApi = LoginApi()
    /**
     * 登录
     *
     * @param name          用户
     * @param password      密码
     * @param loginCallBack 登录回调接口
     */
    fun logon(
        name: String,
        password: String,
        loginCallBack: NetworkCallback<BaseModel<String>>
    ) {
        loginApi.logon(name, password, loginCallBack)
    }

    /**
     * 注册账号
     *
     * @param name         用户名
     * @param password     密码
     * @param registerTime 注册时间
     */
    fun register(
        name: String,
        password: String,
        registerTime: String
    ) {
        loginApi.register(name, password, registerTime, registerCallBack)
    }

    private val registerCallBack: NetworkCallback<BaseModel<String>> =
        object : NetworkCallback<BaseModel<String>> {
            override fun onServiceException() {
                application.applicationContext.toastCustomTime("网络超时", 200)
            }

            override fun onServiceFailure() {
                application.applicationContext.onServiceFailure()
            }

            override fun onSessionTimeout() {
//                application.applicationContext.onSessionTimeout()
            }
            override   fun onChangeData(data: BaseModel<String>?) {
                application.applicationContext.toast( data?.msg?:"")
            }
        }

    fun initData() {
        generateDimensFile("dimens-320x480px.xml", 2.0, 1 / 2.toDouble())
        generateDimensFile("dimens-480x800px.xml", 2.0, 0.88)
        generateDimensFile("dimens-1280x720px.xml", 2.0, 1.0)
        generateDimensFile("dimens-1920x10800px.xml", 2.0, 1.5)
    }

    /**
     * 产生dimens文件（包含dp（1~500）与sp（1~100））
     *
     * @param fileName 文件名
     * @param standard 以（1280x720为基准 1dp =2px
     * @param multiple 转换倍数
     */
    private fun generateDimensFile(
        fileName: String,
        standard: Double,
        multiple: Double
    ) {
        Observable.create<String> { emitter ->
            val path = FileUtil.musicDir + fileName
            val builder = StringBuilder()
            builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n")
            builder.append("<resources>").append("\n")
            for (i in 0..999) {
                builder.append("<dimen name=\"").append("px_")
                    .append(i).append("\">")
                    .append(String.format("%.1f", i / standard * multiple))
                    .append("dp</dimen>\n")
            }
            for (i in 0..199) {
                builder.append("<dimen name=\"").append("sp_")
                    .append(i).append("\">")
                    .append(String.format("%.1f", i / standard * multiple))
                    .append("sp</dimen>\n")
            }
            builder.append("</resources>").append("\n")
            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream =
                    FileOutputStream(instance!!.createNewFile(path))
                fileOutputStream.write(builder.toString().toByteArray())
                fileOutputStream.flush() //将内容一次性写入文件
                emitter.onNext("写入成功$path")
            } catch (e: IOException) {
                Log.d(TAG, "run() returned: $e")
                e.printStackTrace()
                emitter.onNext("写入失败")
            } finally {
                fileOutputStream?.close()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(s: String) {
                    Log.d(TAG, "onNext: s = $s")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}