package com.dht.music.local

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.*
import com.dht.database.bean.music.MusicBean
import com.dht.database.preferences.MessagePreferences
import com.dht.music.R
import com.dht.music.databinding.FragmentLocalBinding
import com.dht.music.playmusic.PlayMusicActivity
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import kotlinx.android.synthetic.main.fragment_local.*
import java.io.File
import java.util.*

/**
 * @author Administrator
 */
class LocalFragment : BaseFragment() {
    private lateinit var mViewModel: LocalViewModel
    private lateinit var mBinding: FragmentLocalBinding
    private val musicList: ArrayList<MusicBean> = ArrayList()
    private var localAdapter: LocalAdapter? = null
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false)
        return mBinding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(LocalViewModel::class.java)
        mBinding.localViewModel = mViewModel
        bindViews()
    }


    private val textWatcher: SimpleTextWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            Log.d(TAG, "afterTextChanged: s = $s")
            val list: ArrayList<MusicBean> = ArrayList()
            for (music in musicList) {
                if (music.name.contains(s.toString()) || music.name.contains(s.toString().toUpperCase())) {
                    list.add(music)
                }
            }
            localAdapter!!.setChangeList(list)
        }
    }

    override fun onResume() {
        super.onResume()
        if (localMusicTitleView != null) {
            localMusicTitleView.updateView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun bindViews() {
        super.bindViews()
        localMusicTitleView.setActivity(activity as BaseActivity)
        localTopTitleView.updateView(activity as BaseActivity, getString(R.string.local_music))
        localTopTitleView.setLocalTitleBar()
        localTopTitleView.setSearchEditTextWatcher(textWatcher)
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        localAdapter = LocalAdapter(recycleItemClickCallBack)
        mViewModel.getMusicData()
            .observe(this, Observer {

                musicList.clear()
                musicList.addAll(it)
                localAdapter!!.setChangeList(it)

            })
        recyclerView.adapter = localAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(VerticalDecoration(3))
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
                if (type == LocalAdapter.Type.IV.index) {
                    val files: MutableList<File> =
                        ArrayList()
                    val file = File(value?.path!!)
                    files.add(file)
                    mViewModel.uploadFiles(files, networkCallback)
                } else {
                    mViewModel.insertOrUpdate(value!!)
                    MessagePreferences.INSTANCE.currentMusic = value
                    val intent = Intent(context, PlayMusicActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    private val networkCallback: NetworkCallback<BaseModel<ArrayList<String>>> =
        object : NetworkCallback<BaseModel<ArrayList<String>>> {
            override fun onServiceException() {
//                context?.onServiceException()
            }

            override fun onServiceFailure() {
//                context?.onServiceFailure()
            }

            override fun onSessionTimeout() {
//                context?.onSessionTimeout()
            }

            override fun onChangeData(data: BaseModel<ArrayList<String>>?) {
                Log.d(TAG, "onChangeData: data = $data")
            }
        }

    companion object {
        private const val TAG = "LocalFragment"
        fun newInstance(): LocalFragment {
            return LocalFragment()
        }
    }
}