package com.dht.interest.investment

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.util.logd
import com.dht.interest.common.api.InvestApi
import com.dht.network.NetworkCallback

/**
 * created by dht on 2020-02-13 19:39
 *
 * @author dht
 */
class InvestViewModel(@NonNull application: Application) : BaseAndroidViewModel(application) {

    private val investApi = InvestApi()


     val listData: MutableLiveData<List<BondBean>> = MutableLiveData()

    fun initInvestmentList() {
        val timestamp = System.currentTimeMillis()
        investApi.getConvertibleBondList(timestamp, object : NetworkCallback<BondListModel> {
            override fun onServiceException() {
                logd("onServiceException")
            }

            override fun onServiceFailure() {
                logd("onServiceException")
            }

            override fun onSessionTimeout() {
                logd("onServiceException")
            }

            override fun onChangeData(data: BondListModel?) {
                logd("data $data")
                val list = data?.rows?.map { it.bean }
                listData.postValue(list)
            }
        })
    }
}