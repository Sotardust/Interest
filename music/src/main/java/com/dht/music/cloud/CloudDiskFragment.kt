package com.dht.music.cloud

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.baselib.util.onServiceException
import com.dht.baselib.util.onServiceFailure
import com.dht.baselib.util.onSessionTimeout
import com.dht.database.bean.music.CloudMusicBean
import com.dht.eventbus.RxBus
import com.dht.eventbus.event.UpdateTopPlayEvent
import com.dht.music.R
import com.dht.music.databinding.FragmentCloudDiskBinding
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import kotlinx.android.synthetic.main.fragment_cloud_disk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 *
 * 云盘音乐页
 *
 * @author Administrator
 */
class CloudDiskFragment : BaseFragment() {

    private lateinit var mViewModel: CloudDiskViewModel
    private lateinit var mBinding: FragmentCloudDiskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cloud_disk, container, false)
        initViews(mBinding.root)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CloudDiskViewModel::class.java)
        mBinding.cloudDisk = mViewModel
        bindViews()
    }

    private var localAdapter: CloudDiskAdapter? = null

    override fun bindViews() {
        super.bindViews()
        cloudTopTitleView.setActivity(activity as BaseActivity)
        cloudTopTitleView.updateView(activity as BaseActivity, "云盘音乐")
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        localAdapter = CloudDiskAdapter(recycleItemClickCallBack)
        val names = ArrayList<String>()
        localAdapter!!.setChangeList(names)
        mViewModel.getMusicList(callback)
        recyclerView.adapter = localAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(VerticalDecoration(3))
    }

    override fun onResume() {
        super.onResume()
        RxBus.INSTANCE.post(UpdateTopPlayEvent())
    }


    private val callback: NetworkCallback<BaseModel<List<CloudMusicBean>>> =
        object : NetworkCallback<BaseModel<List<CloudMusicBean>>> {
            override fun onChangeData(data: BaseModel<List<CloudMusicBean>>?) {
                mViewModel.insertMusicList(data?.result!!)
                handleData()
            }

            override fun onServiceException() {
                context?.onServiceException()
                handleData()
            }

            override fun onServiceFailure() {
                context?.onServiceFailure()
                handleData()
            }

            override fun onSessionTimeout() {
                context?.onSessionTimeout()
            }

        }

    fun handleData() {
        GlobalScope.launch {
            val list = mViewModel.getMusicList()
            withContext(Dispatchers.Main) {
                localAdapter!!.setUsernameList(list?.map { it.name } as ArrayList<String>)
                localAdapter!!.setChangeList(list.map { it.name } as MutableList<String>)
                setRecycleViewVisible(true)
            }
        }
    }

    private val recycleItemClickCallBack: RecycleItemClickCallBack<String> =
        object : RecycleItemClickCallBack<String>() {
            override fun onItemClickListener(value: String?, position: Int?) {
                super.onItemClickListener(value, position)
                Log.d(
                    TAG,
                    "onItemClickListener() called with: value = [$value], position = [$position]"
                )
                mViewModel.downloadMusic(value)
            }
        }

    /**
     * 设置recyclerView 是否可见
     *
     * @param isRecyclerView 是否是RecyclerView
     */
    private fun setRecycleViewVisible(isRecyclerView: Boolean) {
        recyclerView.visibility = if (isRecyclerView) View.VISIBLE else View.GONE
        cloudNoMusic.visibility = if (isRecyclerView) View.GONE else View.VISIBLE
    }

    companion object {
        private const val TAG = "CloudDiskFragment"
        fun newInstance(): CloudDiskFragment {
            return CloudDiskFragment()
        }
    }
}