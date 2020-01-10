package com.dht.baselib.base

import android.view.View

/**
 * 处理TitleBar显示、点击等操作
 * created by dht on 2018/7/3 13:25
 *
 * @author Administrator
 */
interface ITitleBarManager : View.OnClickListener {
    /**
     * 初始化TitleBar视图View
     *
     * @param view view
     */
    fun initViews(view: View?)

    /**
     * 处理点击事件
     */
    fun handlingClickEvents(view: View?)

    /**
     * 设置TitleBar视图控件可见或设置文本内容
     */
    fun setTitleBarVisibilityOrText()
}