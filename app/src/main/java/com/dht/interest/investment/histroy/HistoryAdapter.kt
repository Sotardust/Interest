package com.dht.interest.investment.histroy

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dht.interest.R

/**
 *
 * 可转债收盘价历史记录适配器
 *
 * created by dht on 2020-02-14 16:55
 *
 * @author dht
 */
class HistoryAdapter : BaseQuickAdapter<HistroyBean, BaseViewHolder> {

    constructor(data: List<HistroyBean>) : super(R.layout.recycle_history, data)

    override fun convert(helper: BaseViewHolder?, item: HistroyBean?) {
        helper!!.setText(R.id.history_last_chg_dt, item!!.last_chg_dt)
        helper.setText(R.id.history_price, item.price)
        helper.setText(R.id.history_volume, item.volume)
        helper.setText(R.id.history_ytm_rt, item.ytm_rt)
        helper.setText(R.id.history_premium_rt, item.premium_rt)
        helper.setText(R.id.history_convert_value, item.convert_value)
        val ytmRt = item.ytm_rt
        helper.setTextColor(
            R.id.history_ytm_rt,
            mContext.resources.getColor(if (ytmRt.contains("-")) R.color.green else R.color.red)
        )
        val premiumRt = item.premium_rt
        helper.setTextColor(
            R.id.history_premium_rt,
            mContext.resources.getColor(if (premiumRt.contains("-")) R.color.green else R.color.red)
        )
    }
}