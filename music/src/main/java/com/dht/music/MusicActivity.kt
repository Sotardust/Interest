package com.dht.music

import android.os.Bundle
import android.util.Log
import com.dht.baselib.base.BaseActivity

/**
 * created by dht on 2019/5/23 0023 22:20
 *
 * @author dht
 */
open class MusicActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // activity 都不可见的情况下，进程保活
        Log.d("dht", "onTrimMemory: ")
    }

    companion object {
        private const val TAG = "com.dht.music.MusicActivity"
    }
}