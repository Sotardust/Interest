package com.dht.interest.investment.histroy

import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseViewModel
import com.dht.baselib.util.logd
import com.dht.interest.common.api.InvestApi
import com.dht.interest.investment.fund.FundDataModel
import com.dht.network.NetworkCallback

/**
 * created by dht on 2020-02-14 16:31
 *
 * @author dht
 */
class HistoryViewModel : BaseViewModel() {


    private val investApi = InvestApi()

    private var bondBeanList: List<HistroyBean>? = ArrayList()

    val listData: MutableLiveData<List<HistroyBean>> = MutableLiveData()

    /**
     * 初始化历史数据
     */
    fun initHistoryList(bondId: String, timestamp: Long) {
        investApi.getHistoryData(bondId, timestamp, object : NetworkCallback<HistoryModel> {
            override fun onServiceException() {
            }

            override fun onServiceFailure() {
            }

            override fun onSessionTimeout() {
            }

            override fun onChangeData(data: HistoryModel?) {
//                val list = data?.rows?.map { it.bean }
//                listData.postValue(list)
            }
        })
    }

    /**
     * 初始化基金历史净值数据
     */
    fun initFundHistoryList(bondId: String, timestamp: Long) {
        investApi.getfundHistoryData(bondId, timestamp, object : NetworkCallback<FundDataModel> {
            override fun onServiceException() {
            }

            override fun onServiceFailure() {
            }

            override fun onSessionTimeout() {
            }

            override fun onChangeData(data: FundDataModel?) {
             logd(data.toString())
            }
        })
    }
}