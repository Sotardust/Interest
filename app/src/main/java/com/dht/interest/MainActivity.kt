package com.dht.interest

import android.annotation.SuppressLint
import android.os.Bundle
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.dialog.ExitDialog
import com.dht.interest.home.HomeFragment
import com.dht.music.MusicActivity

/**
 * @author Administrator
 */
class MainActivity : MusicActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        val exitDialog: ExitDialog = ExitDialog.newInstance()
        exitDialog.show(this)
        exitDialog.setOkCallBack(object : LocalCallback<String>() {
            override fun onChangeData() {
                finish()
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}