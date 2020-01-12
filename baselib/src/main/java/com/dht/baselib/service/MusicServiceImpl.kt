package com.dht.baselib.service

import android.os.RemoteException
import com.dht.database.bean.music.IMusicAidlInterface
import com.dht.database.bean.music.MusicBean
import java.util.*

/**
 * @author Administrator
 */
class MusicServiceImpl(private val iBinder: IMusicAidlInterface) {

    /**
     * 播放音乐
     *
     * @param music MusicBean
     */
    fun playMusic(music: MusicBean) {
        try {
            iBinder.playMusic(music.toMusic())
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放当前音乐
     */
    fun playCurrentMusic() {
        try {
            iBinder.playCurrentMusic()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化播放列表
     */
    fun initPlayList() {
        try {
            iBinder.initPlayList()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 更改播放列表
     */
    fun setPlayList(musics: List<MusicBean>) {
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
    fun setPlayType(type: Int) {
        try {
            iBinder.setPlayType(type)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放已暂停的音乐
     */
    fun playPause() {
        try {
            iBinder.playPause()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 暂停
     */
    fun pause() {
        try {
            iBinder.pause()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 停止播放音乐
     */
    fun stop() {
        try {
            iBinder.stop()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放上一首音乐
     */
    fun playPrevious() {
        try {
            iBinder.playPrevious()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 播放下一首音乐
     */
    fun playNext() {
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
    fun seekTo(msec: Int) {
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
    val isLooping: Boolean
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
    val isPlaying: Boolean
        get() {
            try {
                return iBinder.isPlaying
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            return false
        }

    fun position(): Int {
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
    val duration: Int
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
    val currentPosition: Int
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
    private fun getPlayList(): List<MusicBean> {
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
    val currentMusic: MusicBean
        get() {
            try {
                return if (iBinder.currentMusic == null) {
                    getPlayList()[0]
                } else {

                    iBinder.currentMusic?.toMusicBean()!!
                }
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
    fun removeFromQueue(position: Int) {
        try {
            iBinder.removeFromQueue(position)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * 清楚队列
     */
    fun clearQueue() {
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
    fun showDesktopLyric(show: Boolean) {
        try {
            iBinder.showDesktopLyric(show)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun audioSessionId(): Int {
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
    fun release() {
        try {
            iBinder.release()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

}