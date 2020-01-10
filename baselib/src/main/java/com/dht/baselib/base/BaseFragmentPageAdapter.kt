package com.dht.baselib.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 *
 * @author Administrator
 * @date 2018/2/19
 */
class BaseFragmentPageAdapter : FragmentStatePagerAdapter {
    private var fragmentList: List<Fragment>
    private var mTitleList: Array<String>? = null

    constructor(
        fm: FragmentManager?,
        fragmentList: List<Fragment>
    ) : super(fm!!) {
        this.fragmentList = fragmentList
    }

    constructor(
        fm: FragmentManager?,
        fragmentList: List<Fragment>,
        mTitleList: Array<String>?
    ) : super(fm!!) {
        this.fragmentList = fragmentList
        this.mTitleList = mTitleList
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitleList != null) {
            mTitleList!![position]
        } else {
            ""
        }
    }
}