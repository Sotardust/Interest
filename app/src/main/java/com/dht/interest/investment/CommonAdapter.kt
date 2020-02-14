package com.dht.interest.investment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dht.interest.R

/**
 * created by dht on 2020-02-13 22:35
 *
 * @author dht
 */
class CommonAdapter : BaseQuickAdapter<BondBean, BaseViewHolder> {

    constructor(data: List<BondBean>) : super(R.layout.recycle_invest, data)

    override fun convert(helper: BaseViewHolder?, item: BondBean?) {
        helper!!.setText(R.id.bond_id, item!!.bond_id)
        helper.setText(R.id.bond_name, item.bond_nm)
        helper.setText(R.id.bond_price, item.price)
        val rate = item.increase_rt
        helper.setText(R.id.increase_rate, rate)
        helper.setTextColor(
            R.id.increase_rate,
            mContext.resources.getColor(if (rate.contains("-")) R.color.green else R.color.red)
        )


    }
}