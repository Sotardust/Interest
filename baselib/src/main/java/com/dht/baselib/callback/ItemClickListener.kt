package com.dht.baselib.callback

/**
 * RecycleView Item点击事件回调接口
 *
 *
 * created by dht on 2018/7/2 15:28
 */
interface ItemClickListener<T> {
    fun onItemClickListener(type: Int?, value: T?, position: Int?)
    fun onItemClickListener(isSelected: Boolean?, value: T?, position: Int?)
    fun onItemLongClickListener(type: Int?, value: T?, position: Int?)
    fun onItemClickListener(value: T?, position: Int?)
    fun onItemLongClickListener(value: T?, position: Int?)
}