package com.dht.interest.investment.histroy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.util.VerticalDecoration
import com.dht.interest.R
import com.dht.interest.databinding.FragmentHistoryBinding
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.include_title_bar.*
import kotlinx.android.synthetic.main.recycle_history.*

/**
 * created by dht on 2020-02-14 16:26
 *
 * @author dht
 */
class HistoryFragment : BaseFragment() {
    private lateinit var mViewModel: HistoryViewModel
    private lateinit var mBinding: FragmentHistoryBinding

    private var adapter: HistoryAdapter? = null

    private var list: List<HistroyBean> = ArrayList()

    companion object {
        fun newInstance(bundle: Bundle): HistoryFragment {
            val historyFragment = HistoryFragment()
            historyFragment.arguments = bundle
            return historyFragment
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        mBinding.historyViewModel = mViewModel
        bindViews()
        initData()
    }

    override fun bindViews() {
        super.bindViews()
        val bondName = arguments?.getString(HistoryActivity.ARG_BOND_NAME)
        val bondId = arguments?.getString(HistoryActivity.ARG_BOND_ID)
        titleBack.setOnClickListener { activity?.finish() }
        titleContent.text = bondName
        mViewModel.initHistoryList(bondId!!, System.currentTimeMillis())
        mViewModel.initFundHistoryList(bondId, System.currentTimeMillis())

        adapter = HistoryAdapter(ArrayList())
        historyRecycle?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        historyRecycle.addItemDecoration(VerticalDecoration(5))
        historyRecycle.adapter = adapter

        LineChartHelper.INSTANCE.initLineChart(priceLineChart)

        mViewModel.listData.observe(this, Observer {
            adapter?.setNewData(it)
            list = it.reversed()
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = true,
                isVolume = false,
                isYtm = false,
                isPrem = false,
                isValue = false
            )
        })

        history_price.setOnClickListener {
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = true,
                isVolume = false,
                isYtm = false,
                isPrem = false,
                isValue = false
            )
        }
        history_volume.setOnClickListener {
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = false,
                isVolume = true,
                isYtm = false,
                isPrem = false,
                isValue = false
            )
        }
        history_ytm_rt.setOnClickListener {
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = false,
                isVolume = false,
                isYtm = true,
                isPrem = false,
                isValue = false
            )
        }
        history_premium_rt.setOnClickListener {
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = false,
                isVolume = false,
                isYtm = false,
                isPrem = true,
                isValue = false
            )
        }
        history_convert_value.setOnClickListener {
            LineChartHelper.INSTANCE.setChartData(
                priceLineChart,
                context,
                list,
                isPrice = false,
                isVolume = false,
                isYtm = false,
                isPrem = false,
                isValue = true
            )
        }
    }

}