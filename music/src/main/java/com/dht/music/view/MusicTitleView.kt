package com.dht.music.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseApplication
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.MusicBean
import com.dht.database.preferences.MessagePreferences
import com.dht.music.R
import com.dht.music.dialog.PlayListDialogFragment
import com.dht.music.repository.RecentPlayRepository

/**
 * 音乐播放栏
 *
 *
 * created by dht on 2019/1/15 11:08
 *
 * @author Administrator
 */
class MusicTitleView : LinearLayout, View.OnClickListener {
    private lateinit var back: ImageView
    private lateinit var musicRelative: RelativeLayout
    private lateinit var avatar: ImageView
    private lateinit var songName: TextView
    private lateinit var author: TextView
    private lateinit var play: TextView
    private lateinit var playList: TextView
    private var currentMusic: MusicBean? = null
    private var mContext: Context
    private var repository: RecentPlayRepository? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    private fun initView() {
        val view =
            View.inflate(mContext, R.layout.view_title_music, this)
        back = view.findViewById(R.id.music_title_back)
        musicRelative = view.findViewById(R.id.music_relative)
        avatar = view.findViewById(R.id.music_title_avatar)
        songName = view.findViewById(R.id.music_title_song_name)
        author = view.findViewById(R.id.music_title_author)
        play = view.findViewById(R.id.music_title_play)
        playList = view.findViewById(R.id.music_title_play_list)
        back.setOnClickListener(this)
        musicRelative.setOnClickListener(this)
        play.setOnClickListener(this)
        playList.setOnClickListener(this)
    }

    private var fragment: BaseFragment? = null


    fun setFragemt(fragment: BaseFragment) {
        this.fragment = fragment
        repository = RecentPlayRepository(BaseApplication.INSTANCE)
    }

    /**
     * 设置返回按钮可见
     */
    fun setBackViewVisibility() {
        back.visibility = View.VISIBLE
    }

    /**
     * 更新视图View
     */
    fun updateView() {
        if (fragment?.getModel() ==null) return
        Log.d(TAG, "updateView: ")
        val isPlaying = fragment?.getModel()!!.isPlaying
        val value =
            mContext.getString(if (isPlaying) R.string.playing else R.string.pause)
        play.text = value
        currentMusic = fragment?.getModel()?.currentMusic
        if (currentMusic == null) {
            return
        }
        repository!!.insertOrUpdate(currentMusic!!)
        songName.text = currentMusic?.name
        author.text = currentMusic?.author
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.music_title_back) {
            fragment?.activity?.finish()
            return
        }
        if (i == R.id.music_title_play) {
            if (fragment?.getModel()!!.isPlaying) {
                fragment?.getModel()!!.pause()
            } else {
                fragment?.getModel()!!.playPause()
            }
            updateView()
            return
        }
        if (i == R.id.music_title_play_list) {
            val playListDialogFragment: PlayListDialogFragment =
                PlayListDialogFragment.newInstance()
            playListDialogFragment.show(
                fragment!!.activity as BaseActivity,
                object : LocalCallback<MusicBean>() {
                    override fun onChangeData(data: MusicBean?) {
                        super.onChangeData(data)
                        fragment?.getModel()!!.playMusic(data!!)
                        playListDialogFragment.dismiss()
                    }
                })
            return
        }
        if (i == R.id.music_relative) {
            if (MessagePreferences.INSTANCE.currentMusic == null) {
                mContext.toastCustomTime(R.string.no_play_music)
                return
            }
            //                    Bundle bundle = new Bundle();
//                    bundle.putBinder(Key.IBINDER, (IBinder) activity);
//                    Intent intent = new Intent(context, PlayMusicActivity.class);
//                    intent.putExtra(Key.IBINDER, bundle);
//                    intent.putExtra(Key.MUSIC, MessagePreferences.INSTANCE.getCurrentMusic());
//                    context.startActivity(intent);
        }
    }

    companion object {
        private const val TAG = "MusicTitleView"
    }
}