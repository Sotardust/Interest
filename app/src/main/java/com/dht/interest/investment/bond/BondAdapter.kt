package com.dht.interest.investment.bond

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dht.interest.R

/**
 * created by dht on 2020-02-14 14:24
 *
 * @author dht
 */
class BondAdapter : BaseQuickAdapter<String, BaseViewHolder> {

    private var titles: List<String> = ArrayList()

    constructor(data: List<String>, titles: List<String>) : super(R.layout.recycle_bond, data) {
        this.titles = titles
    }


    override fun convert(helper: BaseViewHolder?, item: String?) {
        val index = helper!!.adapterPosition
        helper.setText(R.id.bond_title, titles[index])
        helper.setText(R.id.bond_content, item!!)
        when (index) {
            2, 5, 9, 16, 17 -> {
                helper.setTextColor(
                    R.id.bond_content,
                    mContext.resources.getColor(if (item.contains("-")) R.color.green else R.color.red)
                )
            }
            else ->{
                helper.setTextColor(
                    R.id.bond_content,
                    mContext.resources.getColor(R.color.color_333333)
                )
            }
        }
    }
}