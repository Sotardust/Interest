package com.dht.interest.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.base.BaseFragmentPageAdapter
import com.dht.interest.R
import com.dht.interest.callback.OnPageChangerCallback
import com.dht.interest.databinding.MainFragmentBinding
import com.dht.interest.other.SecondaryLinkageFragment
import com.dht.interest.phone.allcalls.AllCallsFragment
import com.dht.interest.phone.answered.AnsweredFragment
import com.dht.interest.phone.dial.DialFragment
import com.dht.interest.phone.missedcalls.MissedCallsFragment
import com.dht.interest.phone.refuse.RefuseFragment
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : BaseFragment() {
    private var mViewModel: MainViewModel? = null

    private val titles =
        arrayOf("未接来电", "已接来电", "拨打电话", "拒接来电", "全部来电", "二级联动")
    private lateinit var mBinding: MainFragmentBinding

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        bindViews()
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.mainViewModel = mViewModel
        // TODO: Use the ViewModel
    }

    override fun bindViews() {
        super.bindViews()
        val mFragmentList: MutableList<Fragment> = ArrayList()
        mFragmentList.add(MissedCallsFragment.newInstance())
        mFragmentList.add(AnsweredFragment.newInstance())
        mFragmentList.add(DialFragment.newInstance())
        mFragmentList.add(RefuseFragment.newInstance())
        mFragmentList.add(AllCallsFragment.newInstance())
        mFragmentList.add(SecondaryLinkageFragment.newInstance())
        baseViewPager.adapter = BaseFragmentPageAdapter(
            childFragmentManager,
            mFragmentList,
            titles
        )
        baseViewPager.currentItem = 0
        baseViewPager.offscreenPageLimit = 6
        tabLayout.setupWithViewPager(baseViewPager)
        baseViewPager.addOnPageChangeListener(object : OnPageChangerCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                baseViewPager.currentItem = position
            }
        })
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}