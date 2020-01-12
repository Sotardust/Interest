package com.dht.music.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragmentPageAdapter
import com.dht.music.R
import kotlinx.android.synthetic.main.activity_download.*
import java.util.*

/**
 * created by dht on 2019/01/07 18:23:23
 *
 * @author Administrator
 */
class DownloadActivity : BaseActivity() {
    private val titles = arrayOf("正在下载", "下载完成")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        initData()
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
        downloadTopTitle.setActivity(this)
        downloadTopTitle.updateView(this, "下载管理")
        tabLayout?.setupWithViewPager(baseViewPager)
    }
}