package com.dht.baselib.callback

/**
 * 实现RecyclerView Item回调
 *
 *
 * created by dht on 2018/7/2 15:26
 */
open class RecycleItemClickCallBack<T> : ItemClickListener<T> {
    override fun onItemClickListener(type: Int?, value: T?, position: Int?) {
    }

    override fun onItemClickListener(isSelected: Boolean?, value: T?, position: Int?) {
    }

    override fun onItemLongClickListener(type: Int?, value: T?, position: Int?) {
    }

    override fun onItemClickListener(value: T?, position: Int?) {
    }

    override fun onItemLongClickListener(value: T?, position: Int?) {
    }
}