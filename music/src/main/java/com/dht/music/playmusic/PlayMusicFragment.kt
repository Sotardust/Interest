package com.dht.music.playmusic

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.PlayType
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.MusicBean
import com.dht.database.preferences.MessagePreferences
import com.dht.eventbus.RxBus
import com.dht.eventbus.event.UpdateTopPlayEvent
import com.dht.music.R
import com.dht.music.databinding.FragmentPlayMusicBinding
import com.dht.music.dialog.PlayListDialogFragment
import kotlinx.android.synthetic.main.fragment_play_music.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Administrator
 */
class PlayMusicFragment : BaseFragment() {
    private var mViewModel: PlayMusicViewModel? = null
    private lateinit var mBinding: FragmentPlayMusicBinding
    private var mPlayType = 0
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_music, container, false)
        return mBinding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(PlayMusicViewModel::class.java)
        mBinding.playMusicViewModel = mViewModel
        initData()
        bindViews()
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun initData() {
        super.initData()
        val currentMusic: MusicBean = MessagePreferences.INSTANCE.currentMusic

        GlobalScope.launch {
            val isPlaying = service.isPlaying
            if (currentMusic.toString() != service.currentMusic.toString()) {
                service.playMusic(currentMusic)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        RxBus.INSTANCE.post(UpdateTopPlayEvent())
    }

    override fun bindViews() {
        super.bindViews()
        playTopTitleView.setActivity(activity as BaseActivity)
        playTopTitleView.updatePlayView()
        playTopTitleView.setSharedCallback(object : LocalCallback<MusicBean?>() {
            override fun onChangeData(data: MusicBean?) {
                super.onChangeData(data)
                context?.toastCustomTime("功能开发中...")
            }
        })

        playType.text = PlayType.getPlayTypeString(mPlayType)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun handlingClickEvents(view: View?) {
        super.handlingClickEvents(view)
        when (view?.id) {
            R.id.playType -> {
                mPlayType = if (++mPlayType > PlayType.SHUFFLE_PLAYBACK.index) 0.also {
                    mPlayType = it
                } else mPlayType
                service.setPlayType(mPlayType)
                playType.text = PlayType.getPlayTypeString(mPlayType)
                context?.toastCustomTime(PlayType.getPlayTypeString(mPlayType))
            }
            R.id.previous -> {
                service.playPrevious()
                play.setText(R.string.playing)
                context?.toastCustomTime(R.string.previous)
                return
            }
            R.id.play -> {
                if (service.isPlaying) {
                    context?.toastCustomTime(R.string.pause)
                    service.pause()
                } else {
                    service.playPause()
                    context?.toastCustomTime(R.string.playing)
                }
                play.setText(if (service.isPlaying) R.string.playing else R.string.pause)
            }
            R.id.next -> {
                service.playNext()
                play.setText(R.string.playing)
                context?.toastCustomTime(R.string.next)
            }
            R.id.playList -> {
                val dialogFragment = PlayListDialogFragment()
                dialogFragment.show(
                    (activity as BaseActivity),
                    object : LocalCallback<MusicBean>() {
                        override fun onChangeData(data: MusicBean?) {
                            super.onChangeData(data)
                            service.playMusic(data!!)
                            dialogFragment.dismiss()
                        }
                    })
            }
        }


    }

    companion object {
        private const val TAG = "PlayMusicFragment"
        fun newInstance(): PlayMusicFragment {
            return PlayMusicFragment()
        }
    }
}