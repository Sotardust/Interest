package com.dht.network

import android.util.Log
import androidx.annotation.NonNull
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * created by dht on 2018/12/24 10:22
 *
 * @author Administrator
 */
class RetrofitClient private constructor() {


    private val retrofitBuilder: Retrofit.Builder

    private object Holder {
        val helper: RetrofitClient = RetrofitClient()
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 60L
        var INSTANCE = Holder.helper
    }

    init {
        val cookieStore = HashMap<String, List<Cookie>>()
        val okHttpClient: OkHttpClient =
            OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(requestHeader)
                .cookieJar(object : CookieJar {
                    override fun saveFromResponse(@NonNull url: HttpUrl, @NonNull cookies: List<Cookie>) {
                        cookieStore[url.host()] = cookies
                    }

                    @NonNull
                    override fun loadForRequest(@NonNull url: HttpUrl): List<Cookie> {
                        val cookies = cookieStore[url.host()]
                        return cookies ?: ArrayList()
                    }
                })
                .build()
        retrofitBuilder =
            Retrofit.Builder() //添加CallAdapterFactory 用Observable<ResponseBody>接收 添加自定义CallAdapterFactory 则用用Observable<T>接收
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加ConverterFactory 用Call<ResponseBody>接收 添加自定义ConverterFactory 则用Call<T>接收
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .build()
    }

    private val retrofit: Retrofit = retrofitBuilder.build()

    fun <T> create(baseUrl: String, service: Class<T>): T = getRetrofit(baseUrl).create(service)

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    /**
     * 日志拦截器
     *
     * @return HttpLoggingInterceptor
     */
    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val loggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    Log.e(
                        "OkHttp",
                        "log message = $message"
                    )
                })
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }

    /**
     * 请求头header拦截器
     *
     * @return Interceptor
     */
    private val requestHeader: Interceptor
        get() = Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
            //OkHttp 不支持上传中文字符，使用编码URLEncoder.encode(file.getName())
            //使用 URLDecoder.decode(file.getName(), "UTF-8")解码
            builder.addHeader("Content-Type", "application/json; charset=utf-8")
            val requestBuilder =
                builder.method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }

}

