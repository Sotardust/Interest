package com.dht.music.repository

import android.app.Application
import android.util.Log
import com.dht.baselib.callback.LocalCallback
import com.dht.database.BaseDatabase
import com.dht.database.bean.music.MusicBean
import com.dht.database.dao.MusicDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * created by dht on 2019/1/14 10:18
 *
 * @author Administrator
 */
class MusicRepository(application: Application) {

    private val musicDao: MusicDao

    companion object {
        private const val TAG = "MusicRepository"
    }

    init {
        val appDatabase =
            BaseDatabase.getInstance(application.applicationContext)
        musicDao = appDatabase.musicDao
        Log.d(TAG, "MusicRepository: musicDao = $musicDao")
    }

    /**
     * 先获取数据库所有歌曲名 查看是否已经存在若不存在则向数据表中插入数据
     *
     * @param musics Music集合
     */

    fun insertMusic(musics: ArrayList<MusicBean>, localCallback: LocalCallback<String>) {
        GlobalScope.launch {
            val list = musicDao.getAllNames()
            if (list != null && list.isNotEmpty()) {
                musicDao.addMusicEntities(musics.filter { !(list.contains(it.name)) })
            }
            localCallback.onChangeData()
        }
    }


    /**
     * 删除本地音乐文件
     *
     * @param name 歌曲名称
     */
    fun deleteMusic(name: String) {
        GlobalScope.launch {
            musicDao.deleteMusic(name)
        }
    }

    /**
     * 获取所有本地音乐数据
     *
     * @param localCallback 回调接口
     */
    fun getAllMusics(localCallback: LocalCallback<List<MusicBean>>) {
        GlobalScope.launch {
            localCallback.onChangeData(musicDao.getAllMusics())
        }
    }

    /**
     * 获取音乐总个数
     *
     * @param localCallback 回调接口
     */
    fun getMusicTotal(localCallback: LocalCallback<Int>) {
        GlobalScope.launch {
            localCallback.onChangeData(musicDao.getMusicTotal())
        }
    }


}