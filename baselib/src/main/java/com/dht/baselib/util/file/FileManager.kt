package com.dht.baselib.util.file

import android.util.Log
import com.dht.baselib.util.LogUtil
import java.io.File
import java.io.IOException
import java.util.*

/**
 * 文件操作管理工具
 * Created by Administrator on 2018/6/26.
 */
class FileManager private constructor() {
    /**
     * 初始化文件目录
     */
    private fun initDirectory() {
        createDirectory()
    }

    /**
     * 创建文件目录
     */
    private fun createDirectory() {
        val directoryList = ArrayList<String>()
        directoryList.add(PathUtil.MUSIC_PATH)
        directoryList.add(PathUtil.LOG_PATH)
        for (path in directoryList) {
            val directory = File(path)
            if (!directory.exists()) {
                val isSuccessful = directory.mkdirs()
                Log.d(
                    TAG,
                    "createDirectory: isSuccessful$isSuccessful"
                )
            }
        }
    }

    /**
     * 创建新文件
     *
     * @param path 文件路径
     * @return 文件对象
     */
    fun createNewFile(path: String): File {
        LogUtil.writeInfo(TAG, "createNewFile", path)
        val file = File(path)
        try {
            if (!file.exists()) {
                val isSuccessful = file.createNewFile()
                Log.d(
                    TAG,
                    "createNewFile: " + path + if (isSuccessful) "成功" else "失败"
                )
            }
        } catch (e: IOException) {
            LogUtil.writeErrorInfo(
                TAG,
                "createNewFile",
                e.toString()
            )
            e.printStackTrace()
        }
        return file
    }

    companion object {
        private const val TAG = "FileManager"
        @JvmStatic
        var instance: FileManager? = null
            get() {
                if (field == null) {
                    synchronized(FileManager::class.java) {
                        if (field == null) {
                            field = FileManager()
                        }
                    }
                }
                return field
            }
            private set
    }

    init {
        initDirectory()
    }
}