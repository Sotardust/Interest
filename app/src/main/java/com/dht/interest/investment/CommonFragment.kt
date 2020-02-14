package com.dht.interest.investment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.util.VerticalDecoration
import com.dht.baselib.util.logd
import com.dht.interest.R
import com.dht.interest.databinding.FragmentInvestmentCommonBinding
import com.dht.interest.investment.bond.BondActivity
import kotlinx.android.synthetic.main.fragment_investment_common.*
import kotlinx.android.synthetic.main.recycle_invest.*

class CommonFragment : BaseFragment() {

    private lateinit var mViewModel: CommonViewModel
    private lateinit var mBinding: FragmentInvestmentCommonBinding

    private var adapter: CommonAdapter? = null


    companion object {
        private const val ARG_IS_MARKET = "arg_isMarket"
        fun newInstance(isMarket: Boolean): CommonFragment {
            val commonFragment = CommonFragment()
            commonFragment.arguments = Bundle().apply { putBoolean(ARG_IS_MARKET, isMarket) }
            return commonFragment
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_investment_common, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)
        mBinding.commonViewModel = mViewModel
        bindViews()
        initData()
    }

    override fun bindViews() {
        super.bindViews()
        adapter = CommonAdapter(ArrayList())
        more.isInvisible = true
        investCommonRecycle?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        investCommonRecycle.addItemDecoration(VerticalDecoration(20))
        investCommonRecycle.adapter = adapter
        mViewModel.listData.observe(this, Observer {
            adapter?.setNewData(it)
        })
        adapter?.setOnItemClickListener { adapter, _, position ->
            BondActivity.start(context!!, adapter.data[position] as BondBean)
        }
        mSearch.addTextChangedListener {
            if (it.toString().isNotBlank()) {
                mViewModel.searchCode(it.toString())
            } else {
                mViewModel.delete()
            }
        }
        mDelete.setOnClickListener {
            mSearch.setText("")
            mViewModel.delete()
        }
        refresh.setOnClickListener { mViewModel.initInvestmentList() }
    }

    override fun initData() {
        super.initData()
        val isMarket = arguments?.getBoolean(ARG_IS_MARKET)
        logd("isMarket $isMarket")
        llSearch.isVisible = isMarket!!
        mViewModel.initInvestmentList()
    }
}