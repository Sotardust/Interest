package com.dht.interest

import com.dht.baselib.base.BaseApplication
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
        application = this
        install(applicationContext)
        MessagePreferences.INSTANCE.install(applicationContext)
    }

}