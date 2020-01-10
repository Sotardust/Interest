package com.dht.music.cloud

import android.os.Bundle
import com.dht.music.MusicActivity
import com.dht.music.R

/**
 * created by Administrator on 2018/12/27 17:23
 *
 * @author Administrator
 */
class CloudDiskActivity : MusicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CloudDiskFragment.newInstance())
                    .commitNow()
        }
    }
}