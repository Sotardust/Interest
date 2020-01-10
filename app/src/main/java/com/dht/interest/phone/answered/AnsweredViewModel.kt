package com.dht.interest.phone.answered

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.repository.AllCallsRepository
import com.dht.interest.repository.entity.AllCallsEntity
import java.util.*

/**
 * @author Administrator
 */
class AnsweredViewModel(application: Application) :
    BaseAndroidViewModel(application) {
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private var mAnsweredCallsList: MutableLiveData<ArrayList<AllCallsEntity>?>? =
        null
    private val repository: AllCallsRepository = AllCallsRepository(application)
    val answeredCallsList: MutableLiveData<ArrayList<AllCallsEntity>?>
        get() {
            if (mAnsweredCallsList == null) {
                mAnsweredCallsList =
                    MutableLiveData()
                distinctAnsweredCalls()
            }
            return mAnsweredCallsList!!
        }

    /**
     * 获取已接来电通话记录
     * 已接类型：1
     *
     * @return AllCalls实体集合
     */
    private fun distinctAnsweredCalls() {
        repository.getCallsEntities(object : LocalCallback<List<AllCallsEntity>>() {
            override fun onChangeData(data: List<AllCallsEntity>?) {
                mAnsweredCallsList!!.setValue(data as ArrayList<AllCallsEntity>)
            }
        }, "1")
    }

}