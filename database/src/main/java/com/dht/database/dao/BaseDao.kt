package com.dht.database.dao

import androidx.room.*

/**
 *  created by Administrator on 2020/1/9 14:38
 */
@Dao
interface BaseDao<T>{
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity:T)

    @Update
    fun update(entity:T)

    @Delete
    fun delete(entity:T)
}