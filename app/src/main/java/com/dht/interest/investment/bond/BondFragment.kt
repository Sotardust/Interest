package com.dht.interest.investment.bond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.util.VerticalDecoration
import com.dht.interest.R
import com.dht.interest.databinding.FragmentBondBinding
import com.dht.interest.investment.BondBean
import com.dht.interest.investment.histroy.HistoryActivity
import kotlinx.android.synthetic.main.fragment_bond.*
import kotlinx.android.synthetic.main.include_title_bar.*

/**
 * created by dht on 2020-02-14 13:58
 *
 * @author dht
 */
class BondFragment : BaseFragment() {

    private lateinit var mViewModel: BondViewModel
    private lateinit var mBinding: FragmentBondBinding

    private var adapter: BondAdapter? = null

    private var mBondBean: BondBean? = null


    companion object {
        fun newInstance(bundle: Bundle): BondFragment {
            val stockFragment = BondFragment()
            stockFragment.arguments = bundle
            return stockFragment
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bond, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(BondViewModel::class.java)
        mBinding.stockViewModel = mViewModel
        bindViews()
        initData()
    }

    override fun bindViews() {
        super.bindViews()
        titleBack.setOnClickListener { activity?.finish() }
        mBondBean = arguments?.getParcelable(BondActivity.ARG_BEAN)
        titleContent.text = mBondBean?.bond_nm
        val titles = resources.getStringArray(R.array.bondList)
        val list: MutableList<String> = ArrayList()
        list.add(mBondBean!!.bond_id)
        list.add(mBondBean!!.price)
        list.add(mBondBean!!.increase_rt)
        list.add(mBondBean!!.stock_nm)
        list.add(mBondBean!!.sprice)
        list.add(mBondBean!!.sincrease_rt)
        list.add(mBondBean!!.pb)
        list.add(mBondBean!!.convert_price)
        list.add(mBondBean!!.convert_value)
        list.add(mBondBean!!.premium_rt)
        list.add(mBondBean!!.rating_cd)
        list.add(mBondBean!!.put_convert_price)
        list.add(mBondBean!!.force_redeem_price)
        list.add(mBondBean!!.convert_amt_ratio)
        list.add(mBondBean!!.short_maturity_dt)
        list.add(mBondBean!!.year_left)
        list.add(mBondBean!!.ytm_rt)
        list.add(mBondBean!!.ytm_rt_tax)
        list.add(mBondBean!!.volume)
        list.add(mBondBean!!.dblow)
        adapter = BondAdapter(list, titles.toList())
        bondRecycle?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bondRecycle.addItemDecoration(VerticalDecoration(1))
        bondRecycle.adapter = adapter
        adapter?.setOnItemClickListener { _, _, _ ->
            HistoryActivity.start(context!!, mBondBean!!.bond_id, mBondBean!!.bond_nm)
        }
    }

}