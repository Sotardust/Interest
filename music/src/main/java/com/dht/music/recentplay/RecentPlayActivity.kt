package com.dht.music.recentplay

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dht.baselib.base.BaseActivity
import com.dht.music.R

/**
 * created by dht on 2018/12/27 17:23
 *
 * @author Administrator
 */
class RecentPlayActivity : BaseActivity() {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            val recentPlayFragment = RecentPlayFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, recentPlayFragment)
                .commitNow()
        }
    }
}