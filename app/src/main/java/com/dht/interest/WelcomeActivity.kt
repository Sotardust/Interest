package com.dht.interest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.file.FileUtil.createLogFile
import com.dht.interest.login.LoginActivity
import java.util.*

/**
 * created by Administrator on 2018/10/29 14:44
 *
 * @author Administrator
 */
class WelcomeActivity : BaseActivity() {
    private val num = 0
    private var welcomeModel: WelcomeModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        welcomeModel = WelcomeModel(application)
        checkSelfPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(
            TAG,
            " requestCode = [$requestCode], permissions = [" + permissions.contentToString() + "], grantResults = [" + grantResults + "]"
        )
        if (requestCode == REQUEST_CODE) { //请求权限后必须创建日志文件
            createLogFile()
        }
        welcomeModel!!.initDatabaseData(object : LocalCallback<String>() {
            override fun onChangeData(data: String?) {
                Log.d(TAG, "onChangeData: path = $data")
            }
        }, object : LocalCallback<String>() {
            override fun onChangeData() {
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    /**
     * 检查权限并申请
     */
    private fun checkSelfPermission() {
        val permissionList =
            ArrayList<String>()
        for (permission in REQUEST_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) !== PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        ActivityCompat.requestPermissions(
            this,
            permissionList.toTypedArray(),
            REQUEST_CODE
        )
    }

    companion object {
        private const val TAG = "dht_welcome"
        private const val REQUEST_CODE = 1
        private val REQUEST_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        )
    }
}