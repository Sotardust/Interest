package com.dht.database


import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.dht.database.bean.app.DownloadBean
import com.dht.database.bean.music.CloudMusicBean
import com.dht.database.bean.music.MusicBean
import com.dht.database.bean.music.RecentPlayBean
import com.dht.database.dao.CloudMusicDao
import com.dht.database.dao.DownloadDao
import com.dht.database.dao.MusicDao
import com.dht.database.dao.RecentPlayDao


/**
 * created by dht on 2018/7/4 16:37
 *
 * @author Administrator
 */
@Database(
    entities = [MusicBean::class, RecentPlayBean::class, DownloadBean::class, CloudMusicBean::class],
    version = 1,
    exportSchema = false
)
abstract class BaseDatabase : RoomDatabase() {

    /**
     * 操作数据库AllCallsDao抽象接口
     */
    //    public abstract AllCallsDao getAllCallsDao ();

    /**
     * 操作数据库MusicDao抽象接口
     */
    abstract val musicDao: MusicDao
    /**
     * 操作数据库CloudMusicDao抽象接口
     */
    abstract val cloudMusicDao: CloudMusicDao

    /**
     * 操作数据库MusicDao抽象接口
     */
    abstract val recentPlayDao: RecentPlayDao
    //
    //    /**
    //     * 操作数据库ContactDao抽象接口
    //     */
    //    public abstract ContactDao contactDao();
    //
    //    /**
    //     * 操作数据库HistoryDao抽象接口
    //     */
    //    public abstract HistoryDao historyDao();

    abstract val downloadDao: DownloadDao

    companion object {

        private var INSTANCE: BaseDatabase? = null

        fun getInstance(context: Context): BaseDatabase? {
            if (INSTANCE == null) {
                synchronized(BaseDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BaseDatabase::class.java, "interest.db"
                        )
                            .build()
                    }
                }
            }
           return INSTANCE
        }
    }
}
