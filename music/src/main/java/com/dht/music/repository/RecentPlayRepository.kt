package com.dht.music.repository

import android.app.Application
import android.util.Log
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.LogUtil.writeInfo
import com.dht.database.BaseDatabase
import com.dht.database.bean.music.MusicBean
import com.dht.database.bean.music.RecentPlayBean
import com.dht.database.dao.RecentPlayDao
import com.dht.database.preferences.MessagePreferences
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * created by dht on 2019/1/17 15:17
 *
 * @author Administrator
 */
class RecentPlayRepository(application: Application) {
    private val recentPlayDao: RecentPlayDao

    private val handler = CoroutineExceptionHandler { _, exception ->
        writeInfo(TAG, "异常", exception.toString())
    }

    /**
     * 插入或更新最近RecentPlayEntity实体类
     *
     * @param music Music实体
     */
    fun insertOrUpdate(music: MusicBean) {
        GlobalScope.launch(handler) {
            val personId: Long = MessagePreferences.INSTANCE.personId
            val entities = recentPlayDao.getPersonRecentPlayEntity(personId) ?: return@launch

            val names = ArrayList<String>()
            for (entity in entities) {
                names.add(entity.songName)
            }
            //不包含则像数据库中插入数据
            if (!names.contains(music.name)) {
                val entity = RecentPlayBean()
                entity.music = music
                entity.songName = music.name
                entity.personId = personId
                entity.playCount = 1
                entity.playTotal = 1
                entity.playTime = System.currentTimeMillis()
                entity.music = music
                recentPlayDao.addRecentPlayEntity(entity)
                return@launch
            }
            val index = names.lastIndexOf(music.name)
            val entity: RecentPlayBean = entities[index]
            var playCount = 0
            val currentTime = System.currentTimeMillis()
            //大于1周
            if (currentTime - entity.playTime >= 7 * 24 * 3600L) {
                playCount = 1
            } else {
                ++playCount
            }
            recentPlayDao.updateRecentPlayEntity(
                entity.id,
                currentTime,
                playCount,
                entity.playTotal + 1
            )

        }

    }

    /**
     * 从数据库中查找所有对应最近播放歌曲数据
     *
     * @param musicLocalCallback LocalCallback
     */
    fun getRecentPlayEntities(musicLocalCallback: LocalCallback<List<RecentPlayBean>>) {
        GlobalScope.launch(handler) {
            val data = recentPlayDao.getPersonRecentPlayEntity(
                MessagePreferences.INSTANCE.personId
            ) ?: ArrayList()
            musicLocalCallback.onChangeData(data)
        }

    }

    /**
     * 获取最近播放歌曲总个数
     *
     * @param localCallback 回调接口
     */
    fun getPlayTotal(localCallback: LocalCallback<Int?>) {
        GlobalScope.launch(handler) {
            val total = recentPlayDao.getRecentPlayTotal(MessagePreferences.INSTANCE.personId)
            localCallback.onChangeData(total)
        }

    }

    /**
     * 获取所有时间升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    fun getAscRecentAllTime(localCallback: LocalCallback<List<RecentPlayBean>>) {
        GlobalScope.launch(handler) {
            val entities = recentPlayDao.getAscRecentAllTime(MessagePreferences.INSTANCE.personId)
                ?: ArrayList()
            localCallback.onChangeData(entities)
        }
    }

    /**
     * 获取最近播放升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    fun getAscRecentPlayTime(localCallback: LocalCallback<List<RecentPlayBean>>) {

        GlobalScope.launch(handler) {
            val entities = recentPlayDao.getAscRecentPlayTime(MessagePreferences.INSTANCE.personId)
                ?: ArrayList()
            localCallback.onChangeData(entities)
        }
    }

    /**
     * 获取最近一周升序排列播放音乐数据
     *
     * @param localCallback 回调接口
     */
    fun getAscRecentOneWeek(localCallback: LocalCallback<List<RecentPlayBean>>) {
        GlobalScope.launch(handler) {
            val entities = recentPlayDao.getAscRecentOneWeek(
                MessagePreferences.INSTANCE.personId,
                System.currentTimeMillis()
            )
                ?: ArrayList()
            localCallback.onChangeData(entities)
        }
    }

    /**
     * 根据音乐名称删除播放记录
     *
     * @param songName      歌曲名称
     * @param localCallback 回调接口
     */
    fun deleteRecentPlayEntity(songName: String, localCallback: LocalCallback<String?>) {
        GlobalScope.launch(handler) {
            recentPlayDao.deleteRecentPlayEntity(MessagePreferences.INSTANCE.personId, songName)
            localCallback.onChangeData()
        }
    }

    companion object {
        private const val TAG = "RecentPlayRepository"
    }

    init {
        val appDatabase: BaseDatabase =
            BaseDatabase.getInstance(application.applicationContext)
        recentPlayDao = appDatabase.recentPlayDao
        Log.d(
            TAG,
            "MusicRepository: musicDao = $recentPlayDao"
        )
    }
}