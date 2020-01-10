package com.dht.baselib.util.file

import android.os.Environment
import android.util.Log
import com.dht.baselib.util.file.FileManager.Companion.instance
import com.dht.baselib.util.loge
import java.io.BufferedWriter
import java.io.File
import java.io.RandomAccessFile

/**
 * @author dai
 * @date 2018/6/1
 */
object FileUtil {
    private const val TAG = "FileUtil"
    /**
     * 日志文件名称
     */
    const val LOG_FILE = "log"

    /**
     * 创建日志文件log.txt
     */
    fun createLogFile() {
        Log.d(TAG, "createLogFile: ")
        val log = PathUtil.LOG_PATH + LOG_FILE
        instance!!.createNewFile(log)
    }

    @JvmField
    val appHomeDir = Environment.getExternalStorageDirectory()
        .absolutePath + File.separator + "patrol3"//文件统一放在这个目录下

    @JvmField
    val logDir = appHomeDir + File.separator + "logs"

    @JvmField
    val videoDir = appHomeDir + File.separator + "videos"

    /**
     * 图片路径
     */
    @JvmField
    val pictureDir = appHomeDir + File.separator + "pictures"

    /**
     * 待提交的巡检项结果文件目录
     */
    @JvmField
    val resultDir = appHomeDir + File.separator + "results"

    @JvmStatic
    fun writerLogToFile(fileLog: String) {

        //日志生成的日期
        //        val createTime = DateUtil.getDateTimeString(System.currentTimeMillis())
        //        //将写入文件的信息 添加上终端号和生成日期 文件名
        //        val jObj = JSONObject()
        //        val newFileLog = "网络:" + NetworkUtil.isConnected() + "," + fileLog
        //        try {
        //            jObj.put("createTime", createTime)
        //            jObj.put("fileLog", newFileLog)
        //        } catch (e: Exception) {
        //            e.printStackTrace()
        //        }
        //
        //        val logStr = jObj.toString()
        var logFile: RandomAccessFile? = null
        try {

            val targetDir = File(logDir)
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }
            val fileName =
                "${LOG_FILE}.txt"
            val targetFile = File(targetDir, fileName)
            if (!targetFile.exists()) {
                targetFile.createNewFile()
            }
            logFile = RandomAccessFile(targetFile, "rw")
            logFile.seek(targetFile.length())
            logFile.write(fileLog.toByteArray())
            logFile.write("\r\n".toByteArray())
            logFile.close()
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        } finally {
            logFile?.close()
        }
    }


    /**
     * 获取生成的巡检结果文件
     */
    fun getResultFile(fileName: String): File? {
        var targetFile: File? = null
        try {
            val targetDir = File(resultDir)
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }
            targetFile = File(targetDir, fileName)
            if (!targetFile.exists()) {
                targetFile.createNewFile()
            }
        } catch (e: Exception) {
            loge(e)
        }
        return targetFile
    }

    /**
     * 获取图片备注数据列表
     *
     */
    fun getPictures(item: Long): List<String>? {
        val file = File(pictureDir)
        if (!file.exists()) {
            file.mkdirs()
        }
        val list = file.list { it, name ->
            it.exists()
            name.contains(item.toString())
        }!!.toList<String>()
        return list.map { pictureDir + File.separator + it }
    }


    /**
     * 获取视频路径
     */
    fun getVideos(item: Long?): List<String>? {
        val file = File(videoDir)
        if (!file.exists()) {
            file.mkdirs()
        }
        val list = file.list { it, name ->
            it.exists()
            name.contains(item.toString())
        }!!.toList<String>()
        return list.map { videoDir + File.separator + it }
    }


}

/**
 * 逐行向文件中写入内容
 */
fun BufferedWriter.writeLineFile(msg: String) {
    this.write(msg)
    this.newLine()
}

/**
 * 关闭 BufferedWriter
 */
fun BufferedWriter.closeWriter() {
    this.flush()
    this.close()
}