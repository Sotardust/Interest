package com.dht.interest

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.annotation.NonNull
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.LogUtil.writeInfo
import com.dht.baselib.util.ParseUtil.parseAuthor
import com.dht.baselib.util.ParseUtil.parseSongName
import com.dht.baselib.util.ParseUtil.parseType
import com.dht.database.bean.music.MusicBean
import com.dht.music.repository.MusicRepository
import java.io.File
import java.util.*

/**
 * @author Administrator
 */
class WelcomeModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val musicRepository: MusicRepository = MusicRepository(application)
    private val filePaths = ArrayList<File>()
    /**
     * 初始化数据
     */
    fun initDatabaseData(
        pathCallback: LocalCallback<String>, localCallback: LocalCallback<String>
    ) {
        val neteasePath =
            Environment.getExternalStorageDirectory().toString() + File.separator + "netease"
        val localPath =
            Environment.getExternalStorageDirectory().toString() + File.separator + "music"
        val neteaseFile = File(neteasePath)
        val localFile = File(localPath)
        if (filePaths.size != 0) {
            filePaths.clear()
        }
        if (!neteaseFile.exists()) {
            writeInfo(
                TAG,
                "traversalSong",
                neteasePath + "路径不存在"
            )
            Log.d(TAG, "searchSong: " + neteasePath + "路径不存在")
        } else {
            traversingMusicFile(neteaseFile.path, pathCallback)
        }
        if (!localFile.exists()) {
            writeInfo(
                TAG,
                "traversalSong",
                localFile.toString() + "路径不存在"
            )
            Log.d(TAG, "searchSong: " + localFile + "路径不存在")
        } else {
            traversingMusicFile(localFile.path, pathCallback)
        }
        val musicList: ArrayList<MusicBean> = ArrayList()
        for (file1 in filePaths) {
            val music = MusicBean()
            music.name = parseSongName(file1.name)
            music.author = parseAuthor(file1.name)
            music.type = parseType(file1.name)
            music.path = file1.path
            music.avatar = null
            music.lyrics = null
            musicList.add(music)
        }
        Log.d(
            TAG,
            "initData: musicList.size = " + musicList.size
        )
        musicRepository.insertMusic(musicList, localCallback)
    }

    /**
     * 遍历查找歌曲文件
     *
     * @param path 路径
     */
    private fun traversingMusicFile(
        path: String,
        localCallback: LocalCallback<String>
    ) {
        val file1 = File(path)
        val files = file1.listFiles()
        for (file in files) {
            val isContains = file.isFile &&
                    (file.name.contains(".mp3") && !file.name.contains(".mp3.") || file.name.contains(
                        ".flac"
                    ))
            if (isContains) {
                localCallback.onChangeData(file.path)
                filePaths.add(file)
            } else if (file.isDirectory) {
                traversingMusicFile(file.path, localCallback)
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}