package com.dht.database.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.dht.database.bean.music.MusicBean
import com.google.gson.Gson

/**
 * @author Administrator
 */
enum class MessagePreferences private constructor() {

    INSTANCE;

    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private val gson = Gson()

    var cookie: String?
        get() = preferences!!.getString(COOKIE, null)
        set(cookie) {
            val editor = preferences!!.edit()
            editor.putString(COOKIE, cookie)
            editor.apply()
        }

    var currentMusic: MusicBean
        get() = gson.fromJson(preferences!!.getString(KEY_MUSIC, null), MusicBean::class.java)
        set(music) {
            editor!!.putString(KEY_MUSIC, gson.toJson(music))
            editor!!.apply()
        }

    var playType: Int
        get() = preferences!!.getInt(KEY_PLAY_TYPE, 0)
        set(playType) {
            editor!!.putInt(KEY_PLAY_TYPE, playType)
            editor!!.apply()
        }

    var isFirstPlay: Boolean
        get() = preferences!!.getBoolean(KEY_IS_FIRST_PLAY, true)
        set(isFirstPlay) {
            editor!!.putBoolean(KEY_IS_FIRST_PLAY, isFirstPlay)
            editor!!.apply()
        }

    var isPlaying: Boolean
        get() = preferences!!.getBoolean(KEY_IS_PLAYING, false)
        set(isPlaying) {
            editor!!.putBoolean(KEY_IS_PLAYING, isPlaying)
            editor!!.apply()
        }

    var personId: Long
        get() = preferences!!.getLong(KEY_PERSON_ID, 123L)
        set(personId) {
            editor!!.putLong(KEY_PERSON_ID, personId)
            editor!!.apply()
        }


    @SuppressLint("CommitPrefEdits")
    fun install(context: Context) {
        preferences = context.applicationContext.getSharedPreferences(
            "message_preferences",
            Context.MODE_PRIVATE
        )
        editor = preferences!!.edit()
    }

    companion object {

        private const val COOKIE = "cookie"
        /**
         * 保存当前播放音乐
         */
        private const val KEY_MUSIC = "music"
        /**
         * 设置是否是第一次播放
         */
        private const val KEY_IS_FIRST_PLAY = "is_first_play"
        /**
         * 设置播放类型
         */
        private const val KEY_PLAY_TYPE = "play_type"
        /**
         * 设置用户id
         */
        private const val KEY_PERSON_ID = "person_id"

        /**
         * 保存前播放状态
         */
        private const val KEY_IS_PLAYING = "is_playing"
    }

}
