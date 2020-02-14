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
class CommonViewModel(@NonNull application: Application) : BaseAndroidViewModel(application) {

    private val investApi = InvestApi()

    private var bondBeanList: List<BondBean>? = ArrayList()

    val listData: MutableLiveData<List<BondBean>> = MutableLiveData()

    /**
     * 初始化可转债数据
     */
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
                bondBeanList = data?.rows?.map { it.bean }
                listData.postValue(bondBeanList)
            }
        })
    }

    /**
     * 搜索可转债名称
     */
    fun searchName(name: String) {
        val list = bondBeanList!!.filter {
            it.bond_nm.contains(name)
        }
        listData.postValue(list)
    }


    /**
     * 搜索代码
     */
    fun searchCode(code: String) {
        val list = bondBeanList!!.filter {
            it.bond_id.contains(code)
        }
        listData.postValue(list)
    }

    /**
     * 删除搜索内容
     */
    fun delete() {
        listData.postValue(bondBeanList)
    }
}