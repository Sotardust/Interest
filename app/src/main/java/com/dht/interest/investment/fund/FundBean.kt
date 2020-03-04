package com.dht.interest.investment.fund

import com.google.gson.annotations.SerializedName

/**
 * created by Administrator on 2020/3/2 10:23
 */
class FundBean {
    /**
     * FSRQ : 2020-02-28
     * DWJZ : 5.6990 //单位净值
     * LJJZ : 5.6990 //累计净值
     * SDATE : null
     * ACTUALSYI :
     * NAVTYPE : 1
     * JZZZL : -4.72 //日涨跌幅
     * SGZT : 限制大额申购
     * SHZT : 开放赎回
     * FHFCZ :
     * FHFCBZ :
     * DTYPE : null
     * FHSP :
     */
    @SerializedName("FSRQ")
    var FSRQ: String? = null
    @SerializedName("DWJZ")
    var DWJZ: String? = null
    @SerializedName("LJJZ")
    var LJJZ: String? = null
    @SerializedName("JZZZL")
    var JZZZL: String? = null
    var FHFCZ: String? = null


    override fun toString(): String {
        return "FundBean(FSRQ=$FSRQ, DWJZ=$DWJZ, LJJZ=$LJJZ, JZZZL=$JZZZL)"
    }


}