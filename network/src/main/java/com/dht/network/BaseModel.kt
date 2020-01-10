package com.dht.network

/**
 * @author Administrator
 */
class BaseModel<T> {
    /**
     * 103 : sessionId 会话超时
     * 102 : 服务器未设置返回结果（默认返回值）
     * 101 : 服务器程序异常
     * 100 : 返回成功
     * 99 : 返回失败
     */
    var code: Int = HttpStatusCode.CODE_102
    var msg: String? = null
    var result: T? = null
}