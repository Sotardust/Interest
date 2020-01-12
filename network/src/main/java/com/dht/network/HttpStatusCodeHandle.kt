package com.dht.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

const val code = 200

fun <T> Response<T>.handlerResponse(callback: NetworkCallback<T>) {
    runBlocking {
        withContext(Dispatchers.Main) {
            when (code()) {
                code -> {
                    val data: T? = body()
                    if (data == null) {
                        callback.onServiceException()
                        return@withContext
                    }
                    when ((data as BaseModel<*>).code) {
                        HttpStatusCode.CODE_103 -> {
                            callback.onSessionTimeout()
                        }
                        HttpStatusCode.CODE_100 -> {
                            callback.onChangeData(data)
                        }
                        HttpStatusCode.CODE_102, HttpStatusCode.CODE_99 -> {
                            callback.onServiceFailure()
                        }

                        HttpStatusCode.CODE_101 -> {
                            callback.onServiceException()
                        }
                        else -> {
                            callback.onServiceException()
                        }
                    }
                }
                else -> {
                    callback.onServiceException()
                }
            }
        }
    }
}
