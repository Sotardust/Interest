package com.dht.music.recentplay

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.database.bean.music.RecentPlayBean
import com.dht.music.R
import com.dht.music.databinding.RecycleItemRecentPlayBinding
import com.dht.music.util.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

/**
 * created by Administrator on 2018/12/27 16:37
 *
 * @author Administrator
 */
class RecentPlayAdapter(
    recycleItemClickCallBack: RecycleItemClickCallBack<RecentPlayBean>,
    private val context: Context
) : BaseAdapter<RecentPlayBean>() {
    private var dynamicType = DynamicType.PLAY_TIME
    private val format =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    override fun setChangeList(mList: List<RecentPlayBean>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    fun setChangeList(
        mList: List<RecentPlayBean?>?,
        dynamicType: DynamicType
    ) {
        this.dynamicType = dynamicType
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding:RecycleItemRecentPlayBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycle_item_recent_play, parent, false
        )
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

    @SuppressWarnings("Unchecked")
    override fun onBindVH(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            val entity: RecentPlayBean? = mList[position]
            (holder as ViewHolder<RecycleItemRecentPlayBinding>).mBinding.recentPlayEntity = entity
            holder.mBinding.index = position
            var dynamicValue: String? = null
            when (dynamicType) {
                DynamicType.PLAY_TOTAL -> dynamicValue =
                    context.getString(R.string.recent_play_count)
                        .replace("[value]", java.lang.String.valueOf(entity?.playTotal))
                DynamicType.PLAY_COUNT -> dynamicValue =
                    context.getString(R.string.recent_play_count)
                        .replace("[value]", java.lang.String.valueOf(entity?.playCount))
                DynamicType.PLAY_TIME -> dynamicValue = context.getString(R.string.recent_play_time)
                    .replace("[value]", format.format(entity?.playTime))
                else -> {
                }
            }
            holder.mBinding.dynamicValue = dynamicValue
        }
    }

    enum class Type(var index: Int) {
        // text文本
        TV(0),  //more 图片
        IV(1);

    }

    enum class DynamicType(var index: Int) {
        // 最近播放时间
        PLAY_TIME(0),  //最近一周播放次数
        PLAY_COUNT(1),  //所有播放次数
        PLAY_TOTAL(2);

    }

    init {
        callBack = recycleItemClickCallBack!!
    }
}