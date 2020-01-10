package com.dht.baselib.base

/**
 * created by Administrator on 2019/1/7 17:51
 *
 * @author Administrator
 */
interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}