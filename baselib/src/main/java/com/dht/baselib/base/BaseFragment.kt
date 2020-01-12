package com.dht.baselib.base

import android.content.ServiceConnection
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dht.baselib.service.MusicServiceImpl

/**
 * 管理Fragment视图
 *
 *
 * created by dht on 2018/6/29 14:47
 *
 * @author Administrator
 */
open class BaseFragment : Fragment(), ITitleBarManager {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onResume() {
        super.onResume()


    }

    override fun onDestroy() {
        super.onDestroy()
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

        private var connection: ServiceConnection? = null

        private var mModel: MusicServiceImpl? = null

        val service = BaseApplication.serviceImpl
    }

}