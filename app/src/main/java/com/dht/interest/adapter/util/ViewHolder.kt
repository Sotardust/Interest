package com.dht.interest.adapter.util

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * created by dht on 2018/7/4 09:33
 *
 * @author Administrator
 */
class ViewHolder<T : ViewDataBinding>(val mBinding: T) :
    RecyclerView.ViewHolder(mBinding.root)