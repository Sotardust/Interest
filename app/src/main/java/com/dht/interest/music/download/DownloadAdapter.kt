package com.dht.interest.music.download

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.database.bean.music.MusicBean
import com.dht.interest.R
import com.dht.interest.common.adapter.util.ViewHolder
import com.dht.interest.databinding.RecycleItemDownloadBinding
import com.dht.interest.music.download.WaveProgressView.OnAnimationListener
import java.text.DecimalFormat

/**
 * created by dht on 2019/3/11 17:43
 */
class DownloadAdapter(clickCallBack: RecycleItemClickCallBack<MusicBean>) :
    BaseAdapter<MusicBean>() {
    override fun setChangeList(mList: List<MusicBean>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding: RecycleItemDownloadBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycle_item_download,
            parent,
            false
        )
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

   override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            val music: MusicBean = mList[position]
            (holder as ViewHolder<RecycleItemDownloadBinding>).mBinding.songName.text = music.name
            holder.mBinding.author.text = music.author
            holder.mBinding.index = position
            val waveProgressView: WaveProgressView = holder.mBinding.waveProgress
//                        waveProgressView.setTextView(textProgress);
            waveProgressView.setOnAnimationListener(object : OnAnimationListener {
                override fun howToChangeText(
                    interpolatedTime: Float,
                    updateNum: Float,
                    maxNum: Float
                ): String {
                    val decimalFormat = DecimalFormat("0.00")
                    return decimalFormat.format(interpolatedTime * updateNum / maxNum * 100.toDouble()) + "%"
                }

                override fun howToChangeWaveHeight(
                    percent: Float,
                    waveHeight: Float
                ): Float {
                    return (1 - percent) * waveHeight
                }
            })
            waveProgressView.setProgressNum(95f, 3000)
            waveProgressView.setDrawSecondWave(true)
        }
    }

    init {
        callBack = clickCallBack!!
    }
}