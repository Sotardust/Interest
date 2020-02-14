package com.dht.interest.common.api

import com.dht.baselib.util.BASE_URL
import com.dht.baselib.util.loge
import com.dht.interest.common.api.service.LoginService
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import com.dht.network.RetrofitClient
import com.dht.network.handlerResponse
import kotlinx.coroutines.*

/**
 * @author Administrator
 */
class LoginApi {
    // 使用本机iP地址 不能使用 127.0.0.1（虚拟机把其作为自身IP）
//    private static final String BASE_URL = "http://192.168.1.71:8080/message/";
//    private static final String BASE_URL = "http://47.104.197.255:8080/message/";
    /**
     * 登录
     *
     * @param name            用户
     * @param password        密码
     * @param networkCallback 回调接口
     */
    fun logon(
        name: String,
        password: String,
        networkCallback: NetworkCallback<BaseModel<String>>
    ) {

        val handler = CoroutineExceptionHandler { _, throwable ->

            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            val service = RetrofitClient.INSTANCE.create(BASE_URL, LoginService::class.java)
            val call = service.login(name, password)
            val response = call.execute()
            response.handlerResponse(networkCallback)
        }
    }

    /**
     * 注册账号
     *
     * @param name            用户名
     * @param password        密码
     * @param registerTime    注册时间
     * @param networkCallback 回调接口
     */
    fun register(
        name: String,
        password: String,
        registerTime: String,
        networkCallback: NetworkCallback<BaseModel<String>>
    ) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            withContext(Dispatchers.IO) {
                val response =
                    RetrofitClient.INSTANCE.create(BASE_URL, LoginService::class.java)
                        .register(name, password, registerTime).execute()
                response.handlerResponse(networkCallback)
            }
        }
    }

    companion object {
        private const val TAG = "BaseModel"
    }
}