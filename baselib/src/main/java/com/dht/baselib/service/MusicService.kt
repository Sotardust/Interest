package com.dht.baselib.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Build
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.dht.baselib.util.PlayType
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.IMusicAidlInterface
import com.dht.database.bean.music.Music
import com.dht.database.bean.music.MusicBean
import com.dht.database.preferences.MessagePreferences
import com.dht.eventbus.RxBus.Companion.INSTANCE
import com.dht.eventbus.event.InitPlayListEvent
import java.io.IOException
import java.util.*

/**
 * created by dht on 2019/1/10 16:15
 *
 * @author Administrator
 */
class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    /**
     * 启动app初始化一次后不再进行 初始化
     */
    private var isFirstInit = true

    private var musicList: List<Music> = ArrayList()
    private var isNext = true
    private var currentPlayIndex = 0
    private var isFirst = true
    private var currentMusic: Music? = null
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.setOnPreparedListener { mp ->
            Log.d(
                TAG,
                "onPrepared() called with: mp = [$mp]"
            )
            if (isNext) {
                ++currentPlayIndex
            } else {
                --currentPlayIndex
            }
            mp.start()
            Log.d(
                TAG,
                "onPrepared: isPlaying = " + mp.isPlaying
            )
        }
        mediaPlayer?.setOnCompletionListener(OnCompletionListener { mp ->
            Log.d(
                TAG,
                "onCompletion() called with: mp = [$mp]"
            )
            try {
                if (isFirst) {
                    isFirst = false
                    return@OnCompletionListener
                }
                iBinder.playNext()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        })
        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            Log.d(
                TAG,
                "onError() called with: mp = [$mp], what = [$what], extra = [$extra]"
            )
            if (!mp.isPlaying) {
                mp.start()
            }
            false
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: ")
        return iBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        try {
            isFirstInit = true
            iBinder.release()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private var iBinder: IMusicAidlInterface.Stub = object : IMusicAidlInterface.Stub() {
        override fun playMusic(music: Music) {
            synchronized(MusicBean::class.java) {
                try {
                    if (musicList.contains(music)) {
                        currentPlayIndex = musicList.lastIndexOf(music)
                    }
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(music.path)
                    mediaPlayer?.prepare()
                    Log.d("MusicTitleView", "playMusic: music = $music")
                    this@MusicService.currentMusic = music
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("MusicTitleView", "playMusic: e", e)
                }
            }
        }

        override fun playCurrentMusic() {
            if (this@MusicService.currentMusic == null) { //                ToastUtil.toastCustom(getApplicationContext(), , 500);
                toastCustomTime("数据初始化中")
                return
            }
            playMusic(this@MusicService.currentMusic!!)
        }

        override fun initPlayList() {
            Log.d(TAG, "initPlayList: ")
            if (!isFirstInit) {
                return
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val attrBuilder = AudioAttributes.Builder()
                attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                mediaPlayer?.setAudioAttributes(attrBuilder.build())
            }
            INSTANCE.post(InitPlayListEvent())
            isFirstInit = false
        }

        override fun setPlayType(type: Int) {
            MessagePreferences.INSTANCE.playType = type
            mediaPlayer?.isLooping = type == PlayType.SINGLE_CYCLE.index
        }

        override fun playPause() {
            synchronized(MusicBean::class.java) {
                if (this@MusicService.currentMusic == null) {
                    playMusic(MessagePreferences.INSTANCE.currentMusic.toMusic())
                } else {
                    mediaPlayer?.start()
                }
            }
        }

        override fun pause() {
            synchronized(MusicBean::class.java) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                }
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                }
            }
        }

        override fun stop() {
            synchronized(MusicBean::class.java) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                }
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.stop()
                }
            }
        }

        override fun playPrevious() {
            synchronized(MusicBean::class.java) {
                isNext = false
                val playType =
                    PlayType.values()[MessagePreferences.INSTANCE.playType]
                when (playType) {
                    PlayType.LIST_LOOP, PlayType.PLAY_IN_ORDER -> {
                        if (currentPlayIndex >= musicList.size) {
                            currentPlayIndex = 0
                        }
                        if (currentPlayIndex <= -1) {
                            currentPlayIndex = musicList.size - 1
                        }
                        playMusic(musicList[currentPlayIndex])
                    }
                    PlayType.SHUFFLE_PLAYBACK -> {
                    }
                    else -> {
                    }
                }
            }
        }

        override fun playNext() {
            synchronized(MusicBean::class.java) {
                isNext = true
                val playType =
                    PlayType.values()[MessagePreferences.INSTANCE.playType]
                when (playType) {
                    PlayType.LIST_LOOP, PlayType.PLAY_IN_ORDER -> {
                        if (currentPlayIndex >= musicList.size) {
                            currentPlayIndex = 0
                        }
                        if (currentPlayIndex <= -1) {
                            currentPlayIndex = musicList.size - 1
                        }
                        playMusic(musicList[currentPlayIndex])
                    }
                    PlayType.SHUFFLE_PLAYBACK -> {
                        val random = Random()
                        val index = random.nextInt(musicList.size)
                        playMusic(musicList[index])
                    }
                    else -> {
                    }
                }
            }
        }

        override fun seekTo(msec: Int) {
            mediaPlayer?.seekTo(msec)
        }

        override fun isLooping(): Boolean {
            return mediaPlayer!!.isLooping
        }

        override fun isPlaying(): Boolean {
            return mediaPlayer!!.isPlaying
        }

        override fun position(): Int {
            return mediaPlayer!!.currentPosition
        }

        override fun getDuration(): Int {
            return mediaPlayer!!.duration
        }

        override fun getCurrentPosition(): Int {
            return mediaPlayer!!.currentPosition
        }

        override fun getPlayList(): List<Music> {
            return musicList
        }

        override fun setPlayList(musics: List<Music>) {
            musicList = musics
        }

        override fun getCurrentMusic(): Music? {
            if (this@MusicService.currentMusic == null) {
                val current = MessagePreferences.INSTANCE.currentMusic ?: return null
                return current.toMusic()
            } else {
                return this@MusicService.currentMusic
            }
        }

        override fun removeFromQueue(position: Int) {}
        override fun clearQueue() {}
        override fun showDesktopLyric(show: Boolean) {}
        override fun audioSessionId(): Int {
            return 0
        }

        override fun release() {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
    }

    companion object {
        private const val TAG = "dht"
    }
}