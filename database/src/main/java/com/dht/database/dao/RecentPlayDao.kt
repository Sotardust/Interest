package com.dht.database.dao

import androidx.room.*
import com.dht.database.bean.music.RecentPlayBean

/**
 * created by dht on 2019/1/17 15:17
 *
 * @author Administrator
 */
@Dao
interface RecentPlayDao {
    /**
     * 像数据表中插入RecentPlayEntity数据
     *
     * @param entities RecentPlayEntity实体集合
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecentPlayEntities(entities: List<RecentPlayBean>)

    /**
     * 像数据表中插入RecentPlayEntity数据
     *
     * @param entity RecentPlayEntity实体
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecentPlayEntity(entity: RecentPlayBean)

    /**
     * 更新RecentPlayEntity
     *
     * @param entity RecentPlayEntity
     */
    @Update
    fun updateRecentPlayEntity(entity: RecentPlayBean)

    /**
     * 根据id 更新对应数据
     *
     * @param id        主键id
     * @param playTime  最近一次播放时间
     * @param playCount 最近播放次数 一周内
     * @param playTotal 所有播放次数
     */
    @Query("update recent_play set play_time=:playTime,recent_play_count=:playCount,play_total=:playTotal where recent_id = :id")
    fun updateRecentPlayEntity(
        id: Int,
        playTime: Long,
        playCount: Int,
        playTotal: Int
    )

    /**
     * 查找人员对应所有最近播放音乐数据
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play where person_id =:personId")
    fun getPersonRecentPlayEntity(personId: Long): List<RecentPlayBean>?

    /**
     * 根据音乐名称删除数据表中对应数据
     *
     * @param name 歌名
     */
    @Query("delete from recent_play where name = :name and person_id =:personId")
    fun deleteRecentPlayEntity(personId: Long, name: String)

    /**
     * 以id为索引统计最近播放总个数
     *
     * @return 本地最近播放音乐个数
     */
    @Query("select count(recent_id) from recent_play where person_id =:personId ")
    fun getRecentPlayTotal(personId: Long): Int

    /**
     * 查找所有最近播放音乐数据
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play ")
    fun getRecentPlayEntity(): List<RecentPlayBean>?

    /**
     * 获取数据表中所有 歌曲名称
     *
     * @return 歌名集合
     */
    @Query("select name from recent_play where person_id =:personId")
    fun getPersonSongNames(personId: Long): List<String>

    /**
     * 查找人员对应所有最近播放音乐数据升序
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play where person_id =:personId order by play_time ASC")
    fun getAscRecentPlayTime(personId: Long): List<RecentPlayBean>?

    /**
     * 查找人员对应最近一周播放音乐数据升序
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play where person_id =:personId and (:playTime - play_time < (7*24*3600)) order by recent_play_count ASC")
    fun getAscRecentOneWeek(
        personId: Long,
        playTime: Long
    ): List<RecentPlayBean>

    /**
     * 查找人员对应最近一周播放音乐数据升序
     *
     * @return RecentPlayEntity 实体集合
     */
    @Query("select * from recent_play where person_id =:personId order by play_total ASC")
    fun getAscRecentAllTime(personId: Long): List<RecentPlayBean>?
}