package com.dht.baselib.util.file

import android.os.Environment
import com.dht.baselib.BuildConfig
import java.io.File

/**
 * 放置各个文件路径名
 *
 *
 *
 * @author Administrator
 * @date 2018/6/26
 */
object PathUtil {
    /**
     * 以包名作为文件总路径
     */
    private val PATH = Environment.getExternalStorageDirectory()
        .toString() + File.separator + BuildConfig.APPLICATION_ID + File.separator
    /**
     * 数据文件路径
     */
    val MUSIC_PATH = PATH + "data" + File.separator
    /**
     * 日志路径
     */
    @JvmField
    val LOG_PATH = PATH + "log" + File.separator
}