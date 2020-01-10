package com.dht.baselib.music

import android.os.RemoteException
import com.dht.baselib.music.MusicContact.Presenter
import com.dht.database.bean.music.IMusicAidlInterface
import com.dht.database.bean.music.MusicBean
import java.util.*

/**
 * @author Administrator
 */
class MusicModel(private val iBinder: IMusicAidlInterface) : Presenter {

    /**
     * 播放音乐
     *
     * @param music MusicBean
     */
    override fun playMusic(music: MusicBean) {
        try {
            iBinder.playMusic(music.toMusic())
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放当前音乐
     */
    override fun playCurrentMusic() {
        try {
            iBinder.playCurrentMusic()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化播放列表
     */
    override fun initPlayList() {
        try {
            iBinder.initPlayList()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 更改播放列表
     */
    override fun setPlayList(musics: List<MusicBean>) {
        try {
            iBinder.playList = musics.map { it.toMusic() }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 设置播放类型
     *
     * @param type 类型
     */
    override fun setPlayType(type: Int) {
        try {
            iBinder.setPlayType(type)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放已暂停的音乐
     */
    override fun playPause() {
        try {
            iBinder.playPause()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 暂停
     */
    override fun pause() {
        try {
            iBinder.pause()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 停止播放音乐
     */
    override fun stop() {
        try {
            iBinder.stop()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放上一首音乐
     */
    override fun playPrevious() {
        try {
            iBinder.playPrevious()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放下一首音乐
     */
    override fun playNext() {
        try {
            iBinder.playNext()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 指定播放的位置（以毫秒为单位的时间）
     *
     * @param msec 指定位置
     */
    override fun seekTo(msec: Int) {
        try {
            iBinder.seekTo(msec)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 是否循环播放
     *
     * @return 是否循环播放
     */
    override val isLooping: Boolean
        get() {
            try {
                return iBinder.isLooping()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return false
        }

    /**
     * 是否正在播放
     *
     * @return 布尔型
     */
    override val isPlaying: Boolean
        get() {
            try {
                return iBinder.isPlaying
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return false
        }

    override fun position(): Int {
        try {
            return iBinder.position()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取音乐时长
     *
     * @return 时长
     */
    override val duration: Int
        get() {
            try {
                return iBinder.duration
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return 0
        }

    /**
     * 获取当前音乐播放位置
     *
     * @return 位置
     */
    override val currentPosition: Int
        get() {
            try {
                iBinder.currentPosition
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return 0
        }

    /**
     * 获取播放列表
     *
     * @return 音乐列表集合
     */
    override fun getPlayList(): List<MusicBean> {
        try {
            return iBinder.playList.map { it.toMusicBean() }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return ArrayList()
    }


    /**
     * 获取当前音乐
     *
     * @return 音乐实体类
     */
    override val currentMusic: MusicBean
        get() {
            try {
                return iBinder.currentMusic.toMusicBean()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return getPlayList()[0]
        }

    /**
     * 移除 队列
     *
     * @param position 队列下表
     */
    override fun removeFromQueue(position: Int) {
        try {
            iBinder.removeFromQueue(position)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 清楚队列
     */
    override fun clearQueue() {
        try {
            iBinder.clearQueue()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 设置是否显示桌面歌词
     *
     * @param show 是否显示
     */
    override fun showDesktopLyric(show: Boolean) {
        try {
            iBinder.showDesktopLyric(show)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun audioSessionId(): Int {
        try {
            iBinder.audioSessionId()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 回收流媒体资源
     */
    override fun release() {
        try {
            iBinder.release()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

}