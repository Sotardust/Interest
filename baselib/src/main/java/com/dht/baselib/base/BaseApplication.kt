package com.dht.baselib.base

import android.app.Application

/**
 *  created by Administrator on 2020/1/9 17:33
 */
open class BaseApplication : Application() {
    companion object {
       lateinit var INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

    }
}