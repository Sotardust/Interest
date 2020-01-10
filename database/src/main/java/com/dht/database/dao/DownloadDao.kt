package com.dht.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dht.database.bean.app.DownloadBean

/**
 * created by dht on 2019/1/17 15:17
 */
@Dao
interface DownloadDao {
    /**
     * 像数据表中插入Music数据
     *
     * @param entities Music实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDownloadEntities(entities: List<DownloadBean>)

    /**
     * 像数据表中插入Music数据
     *
     * @param entity Music实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDownloadEntity(entity: DownloadBean)

    /**
     * 更新下载状态
     *
     * @param state    下载状态
     * @param songName 歌曲名称
     */
    @Query("update download set state=:state where name = :songName")
    fun updateDownloadState(state: Int, songName: String)

    /**
     * 查找正在下载的数据音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from download where state != 2  ")
    fun getDownloadingList(): List<DownloadBean>

    /**
     * 查找下载完成的音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from download where state = 2 ")
    fun getDownloadedList(): List<DownloadBean>
}