package com.dht.music.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.database.bean.music.MusicBean
import com.dht.music.R
import com.dht.music.databinding.RecycleItemPlayListBinding
import com.dht.music.util.ViewHolder

/**
 * 播放列表适配器
 *
 *
 * created by dht on 2018/12/27 16:37
 *
 * @author Administrator
 */
class PlayListAdapter(recycleItemClickCallBack: RecycleItemClickCallBack<MusicBean>) :
    BaseAdapter<MusicBean>() {
    override fun setChangeList(mList: List<MusicBean>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding =
            DataBindingUtil.inflate<RecycleItemPlayListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.recycle_item_play_list, parent, false
            )
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

    override fun onBindVH(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            (holder as ViewHolder<RecycleItemPlayListBinding>).mBinding.music = mList[position]
            holder.mBinding.index = position
        }
    }

    enum class Type(var index: Int) {
        // text文本
        TV(0),  //删除 图片
        IV(1);

    }

    init {
        callBack = recycleItemClickCallBack!!
    }
}