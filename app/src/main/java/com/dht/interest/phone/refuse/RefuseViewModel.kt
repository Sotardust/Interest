package com.dht.interest.phone.refuse

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.repository.AllCallsRepository
import com.dht.interest.repository.entity.AllCallsEntity
import java.util.*

class RefuseViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val repository: AllCallsRepository = AllCallsRepository(application)
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private var mRefuseCallsList: MutableLiveData<ArrayList<AllCallsEntity>>? = null

    val refuseCallsList: MutableLiveData<ArrayList<AllCallsEntity>>
        get() {
            if (mRefuseCallsList == null) {
                mRefuseCallsList = MutableLiveData()
                distinctRefuseCalls()
            }
            return mRefuseCallsList!!
        }

    /**
     * 获取未接来电通话记录
     * 拒接类型：5
     *
     *
     * AllCalls实体集合
     */
    private fun distinctRefuseCalls() {
        repository.getCallsEntities(object : LocalCallback<List<AllCallsEntity>>() {
            override fun onChangeData(data: List<AllCallsEntity>?) {
                mRefuseCallsList?.setValue(data as ArrayList<AllCallsEntity>)
            }
        }, "5")
    }

}