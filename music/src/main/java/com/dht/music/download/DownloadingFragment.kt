package com.dht.music.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseFragment
import com.dht.music.R
import com.dht.music.databinding.FragmentDownloadingBinding

/**
 * @author Administrator
 */
class DownloadingFragment : BaseFragment() {
    private var mViewModel: DownloadingViewModel? = null
    private var mBinding: FragmentDownloadingBinding? = null
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_downloading, container, false)
        return mBinding!!.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DownloadingViewModel::class.java)
        mBinding!!.downloadingViewModel = mViewModel
    }

    companion object {
        fun newInstance(): DownloadingFragment {
            return DownloadingFragment()
        }
    }
}