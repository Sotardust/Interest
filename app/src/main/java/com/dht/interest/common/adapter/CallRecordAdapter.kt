package com.dht.interest.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.interest.R
import com.dht.interest.common.adapter.util.ViewHolder
import com.dht.interest.databinding.RecycleItemAllCallsBinding
import com.dht.interest.repository.entity.AllCallsEntity

/**
 * 展示通话记录列表适配器
 *
 *
 * created by dht on 2018/7/3 18:28
 */
class CallRecordAdapter(@NonNull callBack: RecycleItemClickCallBack<AllCallsEntity>) :
    BaseAdapter<AllCallsEntity>() {
    override fun setChangeList(mList: List<AllCallsEntity>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemAllCallsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.recycle_item_all_calls,
            parent, false
        )
        mBinding.callBack = callBack
        return ViewHolder(mBinding)
    }

    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder<RecycleItemAllCallsBinding>).mBinding.allCallsEntity = mList[position]
        holder.mBinding.index = position
        holder.mBinding.executePendingBindings()
    }

    companion object {
        private const val TAG = "CallRecordAdapter"
    }

    init {
        this.callBack = callBack!!
    }
}