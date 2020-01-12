package com.dht.baselib.util

import android.util.Log
import com.dht.baselib.util.file.FileUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 *  created by dht on 2019/12/12 10:47
 */
private const val TAG = "dht"
private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

/**
 * http请求数据结果，以及提交结果保存到文件中
 * //TODO 讨论保存到一个文件中还是多个文件中
 */
fun LogdHttp(msg: String) {

}

/**
 * ftp文件上传日志，保存到文件中
 */
fun logSftp(success: Boolean, msg: String, upload: Boolean = true) {
    GlobalScope.launch {
        val info =
            "\n当前时间：${format.format(System.currentTimeMillis())}\n文件${if (upload) "上传" else "删除"}成功:$success \n$msg"
        FileUtil.writerLogToFile(info)
        coroutineContext.cancelChildren()
    }

}

fun logd(msg: String) {
    Log.d(TAG, msg)

}

fun logd(tag: String, msg: String) {
    Log.d(tag, msg)
}

/**
 * 程序运行异常统一保存到日志文件中
 * 代码运行抛出异常给出的提示为：抛出异常
 * 程序崩溃给出的提示为：异常退出
 */
fun loge(e: Exception) {
    Log.e(TAG, "e = $e", e)
    GlobalScope.launch {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        e.printStackTrace(printWriter)
        var cause: Throwable? = e.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val info = "\n当前时间：${format.format(System.currentTimeMillis())}\n抛出异常:\n" + "$writer"
        FileUtil.writerLogToFile(info)
        coroutineContext.cancelChildren()
    }
}

fun loge(ex: Throwable) {

    GlobalScope.launch {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val info = "\n当前时间：${format.format(System.currentTimeMillis())}\n抛出异常:\n" + "$writer"
        FileUtil.writerLogToFile(info)
        coroutineContext.cancelChildren()
    }
}

fun loge(tag: String, ex: Exception) {
    Log.e(tag, "e = $ex", ex)
    loge(e = ex)
}
