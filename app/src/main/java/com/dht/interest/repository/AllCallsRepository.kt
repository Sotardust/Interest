package com.dht.interest.repository

import android.app.Application
import com.dht.baselib.callback.LocalCallback
import com.dht.database.BaseDatabase
import com.dht.interest.repository.dao.AllCallsDao
import com.dht.interest.repository.entity.AllCallsEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 对 AllCallsEntity 数据以及数据库进行操作
 *
 *
 * created by Administrator on 2018/10/26 11:19
 */
class AllCallsRepository(application: Application) {
    private val allCallsDao: AllCallsDao? = null
    /**
     * 向数据库中添加AllCallsEntity 实体类
     *
     * @param entity AllCallsEntity
     */
    fun addAllCallsEntity(entity: AllCallsEntity) {
        GlobalScope.launch {
            allCallsDao?.addAllCallsEntity(entity)
        }

    }

    /**
     * 获取数据库中的AllCallsEntity 实体类集合数据
     *
     * @param localCallback 回调接口
     */
    fun getAllCallsEntities(localCallback: LocalCallback<List<AllCallsEntity>>) {
        getCallsEntities(localCallback, "0")
    }

    /**
     * 获取数据库中的AllCallsEntity 实体类集合数据
     *
     * @param localCallback 回调接口
     * @param callType      1/2/3/4/5 接听/拨打/未接//拒接
     */
    fun getCallsEntities(
        localCallback: LocalCallback<List<AllCallsEntity>>,
        callType: String
    ) {
        GlobalScope.launch {
            val entities =
                if ("0" == callType) allCallsDao?.findAllCallsEntities() else allCallsDao?.findCallsEntities(
                    callType
                )

            localCallback.onChangeData(entities ?: ArrayList())
        }

    }

    companion object {
        private const val TAG = "AllCallsRepository"
    }

    init {
        val appDatabase =
            BaseDatabase.getInstance(application.applicationContext)
//                allCallsDao = appDatabase.getAllCallsDao();
    }
}