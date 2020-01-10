package com.tocel.patrol.util

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
import com.dht.baselib.base.BaseApplication


/**
 * Author: wang xiao long
 * Date: 2019-05-08
 */
object NetworkUtil {

    /**
     * Return whether network is connected.
     *
     * Must hold `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
     *
     * @return `true`: connected<br></br>`false`: disconnected
     */
    @JvmStatic
    @RequiresPermission(ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val info = getActiveNetworkInfo()
        @Suppress("DEPRECATION")
        return info != null && info.isConnected
    }

    @Suppress("DEPRECATION")
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private fun getActiveNetworkInfo(): NetworkInfo? {
        val cm =
            BaseApplication.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        @Suppress("DEPRECATION")
        return cm?.activeNetworkInfo
    }

}