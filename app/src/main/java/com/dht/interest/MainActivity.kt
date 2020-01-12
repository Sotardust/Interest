package com.dht.interest

import android.os.Bundle
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.dialog.ExitDialog
import com.dht.interest.home.HomeFragment

/**
 * @author Administrator
 */
class MainActivity : BaseActivity() {
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