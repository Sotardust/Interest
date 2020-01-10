package com.dht.music.dialog

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseDialogFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.MusicBean
import com.dht.music.R
import com.dht.music.databinding.FragmentPlayListDialogBinding
import com.dht.music.playmusic.PlayMusicActivity
import kotlinx.android.synthetic.main.fragment_play_list_dialog.*
import androidx.lifecycle.Observer

/**
 * @author Administrator
 */
class PlayListDialogFragment : BaseDialogFragment() {

    private lateinit var mViewModel: PlayListDialogViewModel
    private lateinit var mBinding: FragmentPlayListDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.PlayListDialog)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBottomWindowParams()
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_play_list_dialog,
            container,
            false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(
            PlayListDialogViewModel::class.java
        )
        mBinding.playListDialogViewModel = mViewModel
        bindViews()
    }


    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        if (window != null) {
            val dm = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(dm)
            window.setLayout(dm.widthPixels, window.attributes.height)
        }
    }



     override fun bindViews() {
        super.bindViews()
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val playListAdapter = PlayListAdapter(recycleItemClickCallBack)
        mViewModel.getMusicData()
            .observe(this, Observer {
                playListAdapter.setChangeList(it)
            })
        recyclePlayList.adapter = playListAdapter
        recyclePlayList.layoutManager = layoutManager
        recyclePlayList.addItemDecoration(VerticalDecoration(3))
    }

    private val recycleItemClickCallBack: RecycleItemClickCallBack<MusicBean> =
        object : RecycleItemClickCallBack<MusicBean>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun onItemClickListener(
                type: Int?,
                value: MusicBean?,
                position: Int?
            ) {
                super.onItemClickListener(type, value, position)
                if (type == PlayListAdapter.Type.IV.index) {
                    context?.toastCustomTime("功能开发中...")
                } else {
                    dismiss()
                    if (localCallback == null) {
                        val intent = Intent(context, PlayMusicActivity::class.java)
                        startActivity(intent)
                    } else {
                        localCallback?.onChangeData(value)
                    }
                }
            }
        }
    private var localCallback: LocalCallback<MusicBean>? = null
    /**
     * 显示播放列表页 并回调点击事件
     *
     * @param activity      BaseActivity
     * @param localCallback 回到接口
     */
    fun show(activity: BaseActivity, localCallback: LocalCallback<MusicBean>) {
        this.localCallback = localCallback
        show(activity)
    }

    companion object {
        private const val TAG = "PlayListDialogFragment"
        fun newInstance(): PlayListDialogFragment {
            return PlayListDialogFragment()
        }
    }
}