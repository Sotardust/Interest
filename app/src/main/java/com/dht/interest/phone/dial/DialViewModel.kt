package com.dht.interest.phone.dial

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.repository.AllCallsRepository
import com.dht.interest.repository.entity.AllCallsEntity
import java.util.*

class DialViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private var mDialCallsList: MutableLiveData<ArrayList<AllCallsEntity>>? = null
    private val repository: AllCallsRepository = AllCallsRepository(application)
    val dialCallsList: MutableLiveData<ArrayList<AllCallsEntity>>?
        get() {
            if (mDialCallsList == null) {
                mDialCallsList = MutableLiveData()
                distinctDialCalls()
            }
            return mDialCallsList
        }

    /**
     * 获取拨打通话记录
     * 已接类型：2
     *
     * @return AllCalls实体集合
     */
    private fun distinctDialCalls() {
        repository.getCallsEntities(object :
            LocalCallback<List<AllCallsEntity>>() {
            override fun onChangeData(data: List<AllCallsEntity>?) {
                mDialCallsList?.setValue(data as ArrayList<AllCallsEntity>)
            }
        }, "2")
    }

}