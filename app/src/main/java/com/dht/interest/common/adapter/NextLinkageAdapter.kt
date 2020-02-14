package com.dht.interest.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.interest.R
import com.dht.interest.common.adapter.util.ViewHolder
import com.dht.interest.databinding.RecycleItemNextLinkageBinding

/**
 * created by Administrator on 2018/12/7 11:23
 *
 * @author Administrator
 */
class NextLinkageAdapter(@NonNull callBack: RecycleItemClickCallBack<*>?) :
    BaseAdapter<String>() {
    private var isShowAll = true
    override fun setChangeList(mList: List<String>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemNextLinkageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.recycle_item_next_linkage,
            parent, false
        )
        mBinding.callBack = callBack
        return ViewHolder(mBinding)
    }

    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            (holder as ViewHolder<RecycleItemNextLinkageBinding>).mBinding.content.setText(
                mList[position]
            )
            (holder ).mBinding.content.text = mList[position]
            holder .mBinding.content.visibility = if (isShowAll) View.VISIBLE else if (position > 2) View.GONE else View.VISIBLE
            holder .mBinding.value = mList[position]
            holder .mBinding.index = position
            holder .mBinding.content.setOnClickListener(
                object : View.OnClickListener {
                    var isClick = false
                    override fun onClick(view: View) {
                        Log.d("dht", "NextLinkageAdapter onClick: ")
                        callBack.onItemClickListener(mList[position], position)
                        holder .mBinding.content.setBackgroundResource(
                            if (isClick) R.drawable.bound_recycle_item else R.drawable.bound_recycle_item_blue)
                        isClick = !isClick
                    }
                })
        }
    }

    fun setShowAll(showAll: Boolean) {
        isShowAll = showAll
        notifyDataSetChanged()
    }

    init {
        this.callBack = callBack!!
    }
}