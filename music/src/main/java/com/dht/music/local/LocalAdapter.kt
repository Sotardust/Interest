package com.dht.music.local

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.database.bean.music.MusicBean
import com.dht.music.R
import com.dht.music.databinding.RecycleItemLocalBinding
import com.dht.music.util.ViewHolder

/**
 * created by Administrator on 2018/12/27 16:37
 *
 * @author Administrator
 */
class LocalAdapter(recycleItemClickCallBack: RecycleItemClickCallBack<MusicBean>) : BaseAdapter<MusicBean>() {
    override fun setChangeList(mList: List<MusicBean>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemLocalBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycle_item_local, parent, false
        )
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            (holder as ViewHolder<RecycleItemLocalBinding>).mBinding.music = mList[position]
            holder.mBinding.index = position
        }
    }

    enum class Type(var index: Int) {
        // text文本
        TV(0),  //more 图片
        IV(1);

    }

    init {
        callBack = recycleItemClickCallBack
    }
}