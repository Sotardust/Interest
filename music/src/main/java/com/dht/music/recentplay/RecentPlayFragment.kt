package com.dht.music.recentplay


import android.content.Intent

import android.os.Build
import android.os.Bundle


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack

import com.dht.baselib.util.VerticalDecoration
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.RecentPlayBean
import com.dht.database.preferences.MessagePreferences
import com.dht.music.R
import com.dht.music.databinding.FragmentRecentPlayBinding
import com.dht.music.local.LocalAdapter
import com.dht.music.playmusic.PlayMusicActivity
import com.dht.music.recentplay.RecentPlayAdapter.DynamicType
import kotlinx.android.synthetic.main.fragment_recent_play.*

class RecentPlayFragment : BaseFragment() {
    private var mViewModel: RecentPlayViewModel? = null
    private lateinit var mBinding: FragmentRecentPlayBinding
    private var recentPlayAdapter: RecentPlayAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var dynamicType = DynamicType.PLAY_TIME
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recent_play, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(RecentPlayViewModel::class.java)
        mBinding.recentPlayViewModel = mViewModel
        initViews(mBinding.root)
        bindViews()
    }

    override fun initViews(view: View?) {
        super.initViews(view)
        recentTopTitleView.updateView(
            activity!!,
            getString(R.string.listening_to_songs)
        )
    }

    override fun bindViews() {
        super.bindViews()
        recentPlayAdapter = RecentPlayAdapter(recycleItemClickCallBack, context!!)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mViewModel?.recentPlayEntityData?.observe(this, Observer<List<RecentPlayBean>> {
                recentRecycler.layoutManager = layoutManager
                recentPlayAdapter!!.setChangeList(it, dynamicType)
            })
        recentRecycler.adapter = recentPlayAdapter
        recentRecycler.addItemDecoration(VerticalDecoration(3))

    }

    private val recycleItemClickCallBack: RecycleItemClickCallBack<RecentPlayBean> =
        object : RecycleItemClickCallBack<RecentPlayBean>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun onItemClickListener(
                type: Int?,
                value: RecentPlayBean?,
                position: Int?
            ) {
                super.onItemClickListener(type, value, position)
                if (type == LocalAdapter.Type.IV.index) {
                    mViewModel!!.deleteCurrentRecentEntity(value?.songName, dynamicType)
                    context?.toastCustomTime( R.string.delete_successful)
                } else {
                    mViewModel?.insertOrUpdate(value?.music!!)
                    MessagePreferences.INSTANCE.currentMusic = value?.music
                    val intent = Intent(context, PlayMusicActivity::class.java)
                    startActivity(intent)
                    onDestroy()
                }
            }
        }
    private var isReverse = true
    override fun handlingClickEvents(view: View?) {
        super.handlingClickEvents(view)
        layoutManager?.reverseLayout = isReverse
        when (view?.id) {
            R.id.recentPlay -> {
                dynamicType = DynamicType.PLAY_TIME
                mViewModel!!.ascRecentPlayTime
            }
            R.id.recentOneWeek -> {
                dynamicType = DynamicType.PLAY_COUNT
                mViewModel!!.ascRecentOneWeek
            }
            R.id.recentAllTime -> {
                dynamicType = DynamicType.PLAY_TOTAL
                mViewModel!!.ascRecentAllTime
            }
        }
        isReverse = !isReverse
    }

    companion object {
        private const val TAG = "RecentPlayFragment"
        fun newInstance(): RecentPlayFragment {
            return RecentPlayFragment()
        }
    }
}