package com.dht.interest

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDex
import com.dht.baselib.base.BaseApplication
import com.dht.baselib.util.logd
import com.dht.database.preferences.MessagePreferences
import com.dht.interest.util.ScreenUtil.install

/**
 * created by Administrator on 2018/10/24 16:58
 *
 * @author Administrator
 */
class MessageApplication : BaseApplication() {


    companion object {
        lateinit var application: MessageApplication
    }

    override fun onCreate() {
        super.onCreate()
        logd("application 开始")
        application = this
        install(applicationContext)
        MessagePreferences.INSTANCE.install(applicationContext)

    }

    override fun onActivityDestroyed(p0: Activity) {
        if (p0 is MainActivity) {
            unbindService(mConnection)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}