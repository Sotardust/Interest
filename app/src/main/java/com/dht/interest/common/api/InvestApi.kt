package com.dht.interest.common.api

import com.dht.baselib.util.FUND_BASE_URL
import com.dht.baselib.util.INVEST_BASE_URL
import com.dht.baselib.util.loge
import com.dht.interest.common.api.service.InvestService
import com.dht.interest.investment.BondListModel
import com.dht.interest.investment.fund.FundDataModel
import com.dht.interest.investment.histroy.HistoryModel
import com.dht.network.NetworkCallback
import com.dht.network.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InvestApi {

    /**
     * 获取可转债数据列表
     *
     * @param timestamp 时间戳
     * @param networkCallback 回调接口
     */
    fun getConvertibleBondList(timestamp: Long, networkCallback: NetworkCallback<BondListModel>) {

        val handler = CoroutineExceptionHandler { _, throwable ->

            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            val service = RetrofitClient.INSTANCE.create(INVEST_BASE_URL, InvestService::class.java)
            val call = service.getConvertibleBondList(timestamp, "Y", "C", "Y", 50)
            val response = call.execute()

            val code = response.code()
            if (code == 200) {
                networkCallback.onChangeData(response.body())
            } else {
                networkCallback.onServiceFailure()

            }
        }
    }

    /**
     * 获取可转债详情信息
     */
    fun getBondDetailData(bondId: String, networkCallback: NetworkCallback<BondListModel>) {
        val handler = CoroutineExceptionHandler { _, throwable ->

            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            //            val service = RetrofitClient.INSTANCE.create(INVEST_BASE_URL, InvestService::class.java)
//            val call = service.getConvertibleBondList(timestamp, "Y", "C", "Y", 50)
//            val response = call.execute()
//
//            val code  = response.code()
//            if (code ==200){
//                networkCallback.onChangeData(response.body())
//            }else{
//                networkCallback.onServiceFailure()
//
//            }
//            response.handlerResponse(networkCallback)
        }
    }

    /**
     * 获取可转债收盘价历史数据
     */
    fun getHistoryData(
        bondId: String,
        timestamp: Long,
        networkCallback: NetworkCallback<HistoryModel>
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->

            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            val service = RetrofitClient.INSTANCE.create(INVEST_BASE_URL, InvestService::class.java)
            val call = service.getHistoryList(bondId, timestamp, 1, 500)
            val response = call.execute()
            val code = response.code()
            if (code == 200) {
                networkCallback.onChangeData(response.body())
            } else {
                networkCallback.onServiceFailure()

            }
        }
    }

    /**
     * 获取基金历史净值
     */
    fun getfundHistoryData(
        bondId: String,
        timestamp: Long,
        networkCallback: NetworkCallback<FundDataModel>
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->

            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            val service = RetrofitClient.INSTANCE.create(FUND_BASE_URL, InvestService::class.java)
            val call = service.getFundHistoryData(
                "jQuery183007241219415352096_1583113519435",
                "519674",
                1,
                20,
                "",
                "",
                timestamp
            )
            val response = call.execute()
            val code = response.code()
            if (code == 200) {
                networkCallback.onChangeData(response.body())
            } else {
                networkCallback.onServiceFailure()

            }
        }
    }

}
