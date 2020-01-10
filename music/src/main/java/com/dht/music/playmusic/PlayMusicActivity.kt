package com.dht.music.playmusic

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dht.music.MusicActivity
import com.dht.music.R

/**
 * created by dht on 2019/01/07 17:50:24
 *
 * @author Administrator
 */
class PlayMusicActivity : MusicActivity() {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            val playMusicFragment = PlayMusicFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, playMusicFragment)
                .commitNow()
        }
    }
}