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
import com.dht.music.databinding.FragmentDownloadedBinding

/**
 * @author Administrator
 */
class DownloadedFragment : BaseFragment() {
    private lateinit var mViewModel: DownloadedViewModel
    private lateinit var mBinding: FragmentDownloadedBinding
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloaded, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DownloadedViewModel::class.java)
        mBinding.downloadedViewModel = mViewModel
    }

    companion object {
        fun newInstance(): DownloadedFragment {
            return DownloadedFragment()
        }
    }
}