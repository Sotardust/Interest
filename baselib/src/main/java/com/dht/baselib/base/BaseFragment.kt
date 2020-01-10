package com.dht.baselib.base

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dht.baselib.music.MusicModel
import com.dht.baselib.service.MusicService
import com.dht.baselib.util.logd
import com.dht.database.bean.music.IMusicAidlInterface

/**
 * 管理Fragment视图
 *
 *
 * created by dht on 2018/6/29 14:47
 *
 * @author Administrator
 */
open class BaseFragment : Fragment(), ITitleBarManager {

    private  var iBinder: IMusicAidlInterface?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("connection = $connection mModel = $mModel")
        if (connection == null) {
            connection = object : ServiceConnection {
                override fun onServiceConnected(
                    name: ComponentName,
                    service: IBinder
                ) {
                    iBinder = IMusicAidlInterface.Stub.asInterface(service)
                    Log.d(TAG, "onServiceConnected: ")
                    if (mModel == null) {
                        mModel = MusicModel(iBinder!!)
                        mModel!!.initPlayList()
                    }
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    Toast.makeText(context, "音乐服务启动失败！", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
        if (context != null) {
            context!!.bindService(
                Intent(
                    activity,
                    MusicService::class.java
                ), connection!!, Context.BIND_AUTO_CREATE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (context != null) {
                context!!.unbindService(connection!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun initViews(view: View?) {}
    override fun handlingClickEvents(view: View?) { //子类中重写改方法处理点击事件
    }

    override fun setTitleBarVisibilityOrText() { //子类中重写改方法设置TitleBar不同控件的显示效果
    }

    override fun onClick(view: View) {
        handlingClickEvents(view)
    }

    /**
     * 绑定视图View
     */
    open fun bindViews() {}

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 该方法处理视图View点击事件以及ViewModel观察监听数据
     */
    fun observerCallback() {}

    companion object {
        private const val TAG = "BaseFragment"

        @JvmStatic
        private var connection: ServiceConnection? = null

        private var mModel: MusicModel? = null

    }

    fun getModel() = mModel
}