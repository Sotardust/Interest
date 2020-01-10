package com.dht.interest.phone.allcalls

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.interest.phone.CallRecordViewModel
import com.dht.interest.repository.entity.AllCallsEntity
import java.util.*

class AllCallsViewModel(@NonNull application: Application) : CallRecordViewModel(application) {
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private var mAllCallsList: MutableLiveData<ArrayList<AllCallsEntity>>? = null

    val allCallsList: MutableLiveData<ArrayList<AllCallsEntity>>
        get() {
            if (mAllCallsList == null) {
                mAllCallsList = MutableLiveData()
//                distinctAllCalls()
            }
            return mAllCallsList!!
        }

    companion object {
        private const val TAG = "AllCallsViewModel"
    }
}