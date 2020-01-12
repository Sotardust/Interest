package com.dht.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dht.database.bean.music.MusicBean

/**
 * 对MusicEntity 存入数据库 提供 添加，更新 查询 删除功能
 *
 *
 * created by dht on 2019/1/11 09:57
 *
 * @author Administrator
 */
@Dao
interface MusicDao  {
    /**
     * 像数据表中插入music数据
     *
     * @param entities Music实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMusicEntities(entities: List<MusicBean>)

    /**
     * 像数据表中插入music数据
     *
     * @param entity Music实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMusicEntity(entity: MusicBean)

    /**
     * 查找所有音乐数据
     *
     * @return Music 实体集合
     */
    @Query("select * from music")
    fun getAllMusics(): List<MusicBean>?

    /**
     * 根据音乐名称删除数据表中对应数据
     *
     * @param name 歌名
     */
    @Query("delete from music where name =:name")
    fun deleteMusic(name: String)

    /**
     * 根据歌曲名称查找对应数据
     *
     * @param name 歌名
     * @return Music实体类
     */
    @Query("select * from music where name =:name")
    fun getMusic(name: String): MusicBean?

    /**
     * 以id为索引统计music总个数
     *
     * @return 本地音乐个数
     */
    @Query("select count(*) from music ")
    fun getMusicTotal(): Int

    /**
     * 获取数据表中所有 歌曲名称
     *
     * @return 歌名集合
     */
    @Query("select name from music")
    fun getAllNames(): List<String>
}