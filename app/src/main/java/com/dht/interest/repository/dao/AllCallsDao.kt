package com.dht.interest.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dht.interest.repository.entity.AllCallsEntity


/**
 * 对AllCallsEntity 存入数据库 提供 添加，更新 查询 删除功能
 *
 * @author Administrator
 */
@Dao
interface AllCallsDao {
    /**
     * 向allCalls表中插入AllCallsEntity数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCallsEntity(entity: AllCallsEntity)

    /**
     * 查找所有通话记录数据
     *
     * @return AllCallsEntity 实体集合
     */
    @Query("select * from allCalls")
    fun findAllCallsEntities(): List<AllCallsEntity>?

    /**
     * 根据类型查找对应的通话记录数据
     *
     * @return AllCallsEntity 实体集合
     */
    @Query("select * from allCalls where call_type = :callType")
    fun findCallsEntities(callType: String?): List<AllCallsEntity>?
}