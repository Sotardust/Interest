package com.dht.interest.music

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.interest.R
import com.dht.interest.common.adapter.util.ViewHolder
import com.dht.interest.databinding.RecycleItemMusicBinding
import java.util.*

/**
 * created by Administrator on 2018/12/27 14:53
 *
 * @author Administrator
 */
class MusicAdapter internal constructor(recycleItemClickCallBack: RecycleItemClickCallBack<String>) :
    BaseAdapter<String>() {
    private var endList: List<Int> = ArrayList()
    private val images = intArrayOf(
        R.mipmap.icon_cherry_original_64,
        R.mipmap.icon_dragonfruit_original_64,
        R.mipmap.icon_egg_original_64,
        R.mipmap.icon_goods_original_64,
        R.mipmap.icon_grape_original_64
    )

    override fun setChangeList(mList: List<String>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    fun setEndList(endList: List<Int>) {
        this.endList = endList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemMusicBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycle_item_music, parent, false
        )
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder<RecycleItemMusicBinding>).mBinding.itemMusicImage.setImageResource(
            images[position]
        )
        holder.mBinding.itemMusicContent.text = mList[position]
        holder.mBinding.itemMusicNumber.text =
            String.format("(%d)", if (endList.isEmpty()) 0 else endList[position])
        holder.mBinding.index = position
    }

    init {
        callBack = recycleItemClickCallBack!!
    }
}