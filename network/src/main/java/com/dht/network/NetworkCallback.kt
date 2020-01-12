package com.dht.network

/**
 *
 *
 * 103 : sessionId 会话超时
 * 102 : 服务器未设置返回结果（默认返回值）
 * 101 : 服务器程序异常
 * 100 : 返回成功
 * 99 : 返回失败
 * 98 : 默认值
 *
 * created by dht on 2018/12/24 13:50
 */
 interface NetworkCallback<T> {

    //100 : 返回成功
    fun onChangeData(data: T?)

    //101  服务器程序异常 ，
    fun onServiceException()

    //102服务器未设置返回结果（默认返回值）,99 返回失败
    fun onServiceFailure()

    //103 会话超时
    fun onSessionTimeout()

}