package com.dht.interest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.interest.R
import com.dht.interest.adapter.util.ViewHolder
import com.dht.interest.databinding.RecycleItemLinkageBinding

/**
 * created by Administrator on 2018/12/7 11:23
 *
 * @author Administrator
 */
class LinkageAdapter(@NonNull callBack: RecycleItemClickCallBack<String>) :
    BaseAdapter<String>() {
    override fun setChangeList(mList: List<String>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    private var lists: List<List<String>>? = null
    private var context: Context? = null
    fun setChangeList(
        context: Context?,
        mList: List<String?>?,
        lists: List<List<String>>?
    ) {
        this.lists = lists
        this.context = context
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemLinkageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.recycle_item_linkage,
            parent, false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder<RecycleItemLinkageBinding>).mBinding.itemContent.text =
            mList[position]
        setFlowLayout(
            holder,
            lists!![position],
            position
        )
    }

    private fun setFlowLayout(
        viewHolder: ViewHolder<RecycleItemLinkageBinding>,
        list: List<String>,
        position: Int
    ) {
        for (value in list) {
            val textView = LayoutInflater.from(context).inflate(
                R.layout.flowlayout_textview,
                viewHolder.mBinding.flowLayout,
                false
            ) as TextView
            textView.text = value
            textView.setOnClickListener(object : View.OnClickListener {
                var isClick = true
                override fun onClick(view: View) {
                    Log.d(
                        "dht",
                        "onClick: index = " + list.indexOf(value) + ", value = " + value
                    )
                    isClick = !isClick
                    textView.setBackgroundResource(if (isClick) R.drawable.bound_recycle_item else R.drawable.bound_recycle_item_blue)
                    callBack.onItemClickListener(value, position)
                }
            })
            viewHolder.mBinding.flowLayout.addView(textView)
            viewHolder.mBinding.flowLayout.setShowLines(number)
        }
    }

    private var number = -1
    fun setShowLines(number: Int) {
        this.number = number
        notifyDataSetChanged()
    }

    init {
        this.callBack = callBack!!
    }
}