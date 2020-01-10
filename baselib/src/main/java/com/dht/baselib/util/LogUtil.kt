package com.dht.baselib.util

import android.util.Log
import com.dht.baselib.util.file.FileUtil
import com.dht.baselib.util.file.PathUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 文件日志存储工具
 * Created by dai on 2018/6/1.
 */
object LogUtil {

    private const val TAG = "LogUtil"

    private val logPath = PathUtil.LOG_PATH + FileUtil.LOG_FILE
    /**
     * 把日志信息写入到文件
     * @param tag 方法名
     * @param info 信息
     */
    private fun write(tag: String, info: String) {
        var fos: FileOutputStream? = null
        try {
            val file = File(logPath)
            fos = FileOutputStream(file, true)
            fos.write(getFormatInfo(tag, info).toByteArray())
            fos.flush()
        } catch (e: IOException) {
            Log.e(TAG, "writeInfo: e", e)
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                Log.e(TAG, "writeInfo: fos e", e)
                e.printStackTrace()
            }
        }
    }

    /**
     * 输入正常信息
     * @param tag 方法名
     * @param msg 信息
     * @param info 信息
     */
    fun writeInfo(tag: String, msg: String, info: String) {
        write(tag, msg + info)
    }

    /**
     * 输入异常信息
     * @param tag 方法名
     * @param msg 信息
     * @param info 信息
     */
    fun writeErrorInfo(tag: String, msg: String, info: String) {
        write(tag, msg + "error： " + info)
    }

    /**
     * 格式化数据
     * @param tag
     * @param info
     * @return 格式化的字符串
     */
    private fun getFormatInfo(tag: String, info: String): String {
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return simpleDateFormat.format(Date()) + " : " + tag + " : " + info + "\n"
    }

    /**
     * 清除log日志
     */
    private fun deleteLogInfo() { //TODO 当log.txt 文件达到一定5M时 清除时间存留较长的文件
    }
}