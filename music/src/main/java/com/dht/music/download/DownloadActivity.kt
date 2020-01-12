package com.dht.music.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dht.baselib.base.BaseFragmentPageAdapter
import com.dht.baselib.base.BaseViewPager
import com.dht.baselib.base.BaseActivity
import com.dht.music.R
import com.google.android.material.tabs.TabLayout
import java.util.*

/**
 * created by dht on 2019/01/07 18:23:23
 *
 * @author Administrator
 */
class DownloadActivity : BaseActivity() {
    private val titles = arrayOf("正在下载", "下载完成")
    private var tabLayout: TabLayout? = null
    private var baseViewPager: BaseViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        bindViews()
        initData()
    }

    /**
     * 绑定视图View
     */
    private fun bindViews() {
        tabLayout = findViewById(R.id.tabLayout)
        baseViewPager = findViewById(R.id.baseViewPager)
    }

    /**
     * 初始化基础数据
     */
    private fun initData() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(DownloadingFragment.newInstance())
        fragmentList.add(DownloadedFragment.newInstance())
        baseViewPager!!.adapter = BaseFragmentPageAdapter(
            supportFragmentManager,
            fragmentList,
            titles
        )
        tabLayout?.setupWithViewPager(baseViewPager)
    }
}