package com.dht.interest.investment.bond

import com.dht.baselib.base.BaseViewModel
import com.dht.interest.common.api.InvestApi

/**
 * created by dht on 2020-02-14 14:08
 *
 * @author dht
 */
class BondViewModel : BaseViewModel() {

    private val investApi = InvestApi()

    fun initStockList(){
//        investApi.getConvertibleBondList()
    }
}