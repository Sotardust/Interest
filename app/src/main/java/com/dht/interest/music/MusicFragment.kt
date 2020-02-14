package com.dht.interest.music

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.interest.R
import com.dht.interest.databinding.FragmentMusicBinding
import com.dht.interest.util.Key
import com.dht.music.cloud.CloudDiskActivity
import com.dht.music.download.DownloadActivity
import com.dht.music.local.LocalActivity
import com.dht.music.recentplay.RecentPlayActivity
import kotlinx.android.synthetic.main.fragment_music.*

/**
 * @author Administrator
 */
class MusicFragment : BaseFragment() {
    private lateinit var mViewModel: MusicViewModel
    private lateinit var mBinding: FragmentMusicBinding
    private var musicAdapter: MusicAdapter? = null
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_music, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        mBinding.musicViewModel = mViewModel
        bindViews()
    }

    override fun bindViews() {
        super.bindViews()
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        musicAdapter = MusicAdapter(recycleItemClickCallBack)
        val list = listOf(*resources.getStringArray(R.array.musicList))
        musicAdapter!!.setChangeList(list)
        mBinding.recyclerView.adapter = musicAdapter
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.addItemDecoration(VerticalDecoration(3))
    }

    override fun onResume() {
        super.onResume()
        if (musicTitleView != null) {
            musicTitleView!!.setActivity(activity as BaseActivity)
            musicTitleView!!.updateView()
        }
        mViewModel.endListData
            .observe(this, Observer {
                musicAdapter?.setEndList(it)
            })
    }

    private val recycleItemClickCallBack: RecycleItemClickCallBack<String> =
        object : RecycleItemClickCallBack<String>() {
            override fun onItemClickListener(
                value: String?,
                position: Int?
            ) {
                super.onItemClickListener(value, position)
                when (position) {
                    0 -> startActivity(
                        Intent(activity, LocalActivity::class.java).putExtra(Key.IBINDER, arguments)
                    )
                    1 -> startActivity(
                        Intent(activity, RecentPlayActivity::class.java).putExtra(
                            Key.IBINDER,
                            arguments
                        )
                    )
                    2 -> startActivity(
                        Intent(activity, CloudDiskActivity::class.java).putExtra(
                            Key.IBINDER,
                            arguments
                        )
                    )
                    3 -> startActivity(
                        Intent(activity, DownloadActivity::class.java).putExtra(
                            Key.IBINDER,
                            arguments
                        )
                    )
                    else -> {
                    }
                }
            }
        }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun newInstance(): MusicFragment {
            return MusicFragment()
        }
    }
}