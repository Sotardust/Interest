package com.dht.interest.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.base.BaseFragmentPageAdapter
import com.dht.interest.R
import com.dht.interest.common.callback.OnPageChangerCallback
import com.dht.interest.common.callback.TabLayoutCallback
import com.dht.interest.databinding.FragmentHomeBinding
import com.dht.interest.investment.InvestFragment
import com.dht.interest.music.MusicFragment
import com.dht.interest.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * @author Administrator
 */
class HomeFragment : BaseFragment() {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private val titles = arrayOf("音乐", "投资", "新闻", "小说", "我的")
    private val images = intArrayOf(
        R.drawable.tablayout_music_bg, R.drawable.tablayout_news_bg, R.drawable.tablayout_news_bg,
        R.drawable.tablayout_novel_bg, R.drawable.tablayout_setting_bg
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return mBinding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        mBinding.homeViewModel = mViewModel
        initViews(mBinding.root)
        bindViews()
    }



    override fun onDestroyView() {
        super.onDestroyView()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun bindViews() {
        super.bindViews()
        val mFragmentList: MutableList<Fragment> = ArrayList()
        val musicFragment: MusicFragment = MusicFragment.newInstance()
        musicFragment.arguments = arguments
        mFragmentList.add(musicFragment)
        mFragmentList.add(InvestFragment.newInstance())
        mFragmentList.add(NewsFragment.newInstance())
        mFragmentList.add(NewsFragment.newInstance())
        mFragmentList.add(NewsFragment.newInstance())
        setCustomTabLayout()
        baseViewPager.offscreenPageLimit = 2
        baseViewPager.adapter = BaseFragmentPageAdapter(
            childFragmentManager,
            mFragmentList,
            titles
        )
        tabLayout.addOnTabSelectedListener(object : TabLayoutCallback() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                super.onTabSelected(tab)
                Log.d(
                    TAG,
                    "onTabSelected: tab.position = " + tab.position
                )
                baseViewPager.currentItem = tab.position
            }
        })
        baseViewPager.addOnPageChangeListener(object : OnPageChangerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(
                    TAG,
                    "onPageSelected: position = $position"
                )
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    /**
     * 自定义布局TabLayout
     */
    private fun setCustomTabLayout() {
        for (i in titles.indices) {
            val tab: TabLayout.Tab = tabLayout.newTab()
            tab.customView = getTabView(i)
            tabLayout.addTab(tab)
        }
    }

    /**
     * 获取对应tab view
     *
     * @param index 下标
     * @return View
     */
    private fun getTabView(index: Int): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.tablayout_home, mBinding.root as ViewGroup, false)
        val imageView =
            view.findViewById<ImageView>(R.id.tab_image)
        imageView.setImageResource(images[index])
        val textView = view.findViewById<TextView>(R.id.tab_content)
        textView.text = titles[index]
        return view
    }

    companion object {
        private const val TAG = "HomeFragment"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}