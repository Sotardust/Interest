package com.dht.interest.common.api.service

import com.dht.network.BaseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    /**
     * 登录接口
     *
     * @param name     用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("name") name: String, @Field("password") password: String): Call<BaseModel<String>>

    /**
     * 注册用户接口
     *
     * @param name         用户名
     * @param password     密码
     * @param registerTime 注册时间
     */
    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name: String, @Field("password") password: String, @Field(
            "register_time") registerTime: String
    ): Call<BaseModel<String>>
}