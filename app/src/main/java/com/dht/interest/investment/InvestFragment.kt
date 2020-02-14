package com.dht.interest.investment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.dht.interest.databinding.FragmentInvestmentBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_investment.*
import java.util.*

class InvestFragment : BaseFragment() {

    private lateinit var mViewModel: InvestViewModel
    private lateinit var mBinding: FragmentInvestmentBinding

    private val titles = arrayOf("市场", "自选股")

    private var marketView: TextView? = null
    private var optionalView: TextView? = null

    companion object {
        fun newInstance(): InvestFragment {
            return InvestFragment()
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_investment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(InvestViewModel::class.java)
        mBinding.investViewModel = mViewModel

        bindViews()
        initData()
    }

    override fun bindViews() {
        super.bindViews()
        setCustomTabLayout()
        val mFragmentList: MutableList<Fragment> = ArrayList()
        mFragmentList.add(CommonFragment.newInstance(true))
        mFragmentList.add(CommonFragment.newInstance(false))
        investViewPager.adapter = BaseFragmentPageAdapter(
            childFragmentManager,
            mFragmentList,
            titles
        )
        investTabLayout.addOnTabSelectedListener(object : TabLayoutCallback() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                super.onTabSelected(tab)
                setViewColor(tab.position)
                investViewPager.currentItem = tab.position
            }
        })
        investViewPager.addOnPageChangeListener(object : OnPageChangerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setViewColor(position)
                investTabLayout.getTabAt(position)?.select()

            }
        })

    }

    /**
     * 自定义布局TabLayout
     */
    private fun setCustomTabLayout() {
        val view1 = LayoutInflater.from(context)
            .inflate(R.layout.tablayout_invest, mBinding.root as ViewGroup, false)
        marketView = view1.findViewById<TextView>(R.id.tab_common)
        marketView?.text = titles.first()
        val view2 = LayoutInflater.from(context)
            .inflate(R.layout.tablayout_invest, mBinding.root as ViewGroup, false)
        optionalView = view2.findViewById<TextView>(R.id.tab_common)
        optionalView?.text = titles.last()
        val marketTab: TabLayout.Tab = investTabLayout.newTab()
        marketTab.customView = view1
        val optionalTab: TabLayout.Tab = investTabLayout.newTab()
        optionalTab.customView = view2
        investTabLayout.addTab(marketTab)
        investTabLayout.addTab(optionalTab)
        setViewColor(0)

    }

    /**
     * 设置tab选中字体颜色
     */
    private fun setViewColor(position: Int) {
        marketView?.setTextColor(resources.getColor(if (position == 0) R.color.orange else R.color.color_333333))
        optionalView?.setTextColor(resources.getColor(if (position == 1) R.color.orange else R.color.color_333333))
    }


}