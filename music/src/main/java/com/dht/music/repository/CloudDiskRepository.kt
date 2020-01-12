package com.dht.music.repository

import android.app.Application
import com.dht.database.BaseDatabase
import com.dht.database.bean.music.CloudMusicBean
import com.dht.database.dao.CloudMusicDao
import com.dht.database.preferences.MessagePreferences

/**
 *
 *  created by dht on 2020/1/6 18:26
 */
class CloudDiskRepository constructor(application: Application) {

    private val TAG = "MusicRepository"

    private var cloudMusicDao: CloudMusicDao? = null

    init {
        val appDatabase = BaseDatabase.getInstance(application.applicationContext)
        cloudMusicDao = appDatabase?.cloudMusicDao
    }

    /**
     * 获取云盘音乐列表
     */
    fun getMusicList(): List<CloudMusicBean>? =
        cloudMusicDao?.getCloudMusicList(MessagePreferences.INSTANCE.personId)

    /**
     * 向库中插入云盘音乐
     */
    fun insertMusic(beans: List<CloudMusicBean>) {
        cloudMusicDao?.addCloudMusicEntities(beans)
    }

    fun getTotal(): Int =
        cloudMusicDao?.getCloudMusicTotal(MessagePreferences.INSTANCE.personId) ?: 0

}