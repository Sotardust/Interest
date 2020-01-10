package com.dht.baselib.music

import com.dht.database.bean.music.Music
import com.dht.database.bean.music.MusicBean

/**
 * @author Administrator
 */
class MusicContact {
    internal interface Presenter {

        fun playMusic(music: MusicBean)
        fun playCurrentMusic()
        /**
         * 初始化播放列表
         */
        fun initPlayList()

        fun setPlayType(type: Int)
        /**
         * 播放已暂停的音乐
         */
        fun playPause() //

        fun pause()
        fun stop()
        fun playPrevious()
        fun playNext()
        /**
         * 指定播放的位置（以毫秒为单位的时间）
         *
         * @param msec int
         */
        fun seekTo(msec: Int)

        /**
         * 是否循环播放
         *
         * @return 是否循环播放
         */
        val isLooping: Boolean

        /**
         * 是否正在播放
         *
         * @return Boolean
         */
        val isPlaying: Boolean

        fun position(): Int
        val duration: Int
        val currentPosition: Int
        fun getPlayList(): List<MusicBean>
        fun setPlayList(musics: List<MusicBean>)
        val currentMusic: MusicBean
        fun removeFromQueue(position: Int)
        fun clearQueue()
        fun showDesktopLyric(show: Boolean)
        fun audioSessionId(): Int
        /**
         * 回收流媒体资源
         */
        fun release()
    }
}