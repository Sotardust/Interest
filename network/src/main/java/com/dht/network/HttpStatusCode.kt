package com.dht.network

/**
 * 服务器返回状态码
 *
 *
 * created by dht on 2020/1/6 17:20
 */
object HttpStatusCode {
    /**
     * 103 : sessionId 会话超时
     * 102 : 服务器未设置返回结果（默认返回值）
     * 101 : 服务器程序异常
     * 100 : 返回成功
     * 99 : 返回失败
     * 98 : 默认值
     */
    const val CODE_99 = 99
    const val CODE_100 = 100
    const val CODE_101 = 101
    const val CODE_102 = 102
    const val CODE_103 = 103
}