package com.dht.baselib.base

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.dht.baselib.service.MusicService
import com.dht.baselib.service.MusicServiceImpl
import com.dht.baselib.util.logd
import com.dht.database.bean.music.IMusicAidlInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  created by Administrator on 2020/1/9 17:33
 */
 abstract class BaseApplication : Application() {

    protected lateinit var mConnection: ServiceConnection

    companion object {
        lateinit var INSTANCE: BaseApplication
        lateinit var serviceImpl: MusicServiceImpl
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        logd("BaseApplication")
        mConnection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val iBinder = IMusicAidlInterface.Stub.asInterface(p1)
                logd("onServiceConnected: ")
                serviceImpl = MusicServiceImpl(iBinder)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                logd("onServiceDisconnected: ")
            }

        }
        GlobalScope.launch {
            bindService(
                Intent(INSTANCE, MusicService::class.java),
                mConnection,
                Context.BIND_AUTO_CREATE
            )
        }
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//            override fun onActivityPaused(p0: Activity) {
//            }
//
//            override fun onActivityStarted(p0: Activity) {
//            }
//
//            override fun onActivityDestroyed(p0: Activity) {
//                logd("Destroyed $p0")
////                onActivityDestroyed(p0)
//            }
//
//            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
//            }
//
//            override fun onActivityStopped(p0: Activity) {
//            }
//
//            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
//            }
//
//            override fun onActivityResumed(p0: Activity) {
//            }
//
//        })

    }

    abstract fun onActivityDestroyed(p0: Activity)
}