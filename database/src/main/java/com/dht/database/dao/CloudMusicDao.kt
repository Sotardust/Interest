package com.dht.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dht.database.bean.app.DownloadBean
import com.dht.database.bean.music.CloudMusicBean

/**
 * 云盘数据
 *
 *
 * created by dht on 2019/1/17 15:17
 */
@Dao
interface CloudMusicDao {
    /**
     * 像数据表中插入CloudMusicBean数据
     *
     * @param entities CloudMusicBean实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCloudMusicEntities(entities: List<CloudMusicBean>)

    /**
     * 像数据表中插入CloudMusicBean数据
     *
     * @param entity CloudMusicBean实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCloudMusicEntity(entity: CloudMusicBean)

    /**
     * 更新下载状态
     *
     * @param state    下载状态
     * @param songName 歌曲名称
     */
    @Query("update download set state=:state where name = :songName")
    fun updateDownloadState(state: Int, songName: String)

    /**
     * 获取所有音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from cloud_music where person_id =:personId")
    fun getCloudMusicList(personId: Long): List<CloudMusicBean>


    /**
     * 获取云盘音乐个数
     *
     * @return MusicBean 实体集合
     */
    @Query("select count(*) from cloud_music where person_id =:personId")
    fun getCloudMusicTotal(personId: Long): Int

    /**
     * 查找下载完成的音乐数据
     *
     * @return MusicBean 实体集合
     */
    @Query("select * from download where state = 2 ")
    fun getDownloadedList(): List<DownloadBean>
}