package com.dht.music.cloud

import android.app.Application
import android.util.Log
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.util.ParseUtil
import com.dht.baselib.util.file.FileManager
import com.dht.baselib.util.file.PathUtil
import com.dht.database.bean.music.CloudMusicBean
import com.dht.music.api.MusicApi
import com.dht.music.repository.CloudDiskRepository
import com.dht.network.BaseModel
import com.dht.network.HttpStatusCode
import com.dht.network.NetworkCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder

class CloudDiskViewModel : BaseAndroidViewModel {

    private var repository: CloudDiskRepository

    constructor(application: Application) : super(application) {
        repository = CloudDiskRepository(application)

    }

    private val musicApi: MusicApi = MusicApi()

    /**
     * 从服务器获取音乐列表
     */
    fun getMusicList(callback: NetworkCallback<BaseModel<List<CloudMusicBean>>>) {
        musicApi.getMusicList(callback)
    }


    /**
     * 从本地获取音乐列表
     */
    fun getMusicList() = repository.getMusicList()

    /**
     * 向库表中插入数据
     */
    fun insertMusicList(bean: List<CloudMusicBean>) {
        GlobalScope.launch {
            val list = repository.getMusicList()
            //若已经存在 则不插入
            val bes = bean.map { item ->
                item.name = ParseUtil.parseSongName(item.name)
                item.path = null
                item.type = ParseUtil.parseType(item.name)
                (list?.map { it.name }?.contains(item.name))
                item
            }
            repository.insertMusic(bes.filter { item ->
                !bean.map { ParseUtil.parseSongName(it.name) }.contains(
                    item.name
                )
            })
        }
    }

    fun downloadMusic(songName: String?) {
        fileName = songName
        musicApi.downloadMusic(URLEncoder.encode(songName), networkCallback)
    }

    private val networkCallback: NetworkCallback<BaseModel<String>> =
        object : NetworkCallback<BaseModel<String>> {


            override fun onChangeData(data: BaseModel<String>?) {
                if (data == null) {
                    return
                }
                if (data.code != HttpStatusCode.CODE_100) {
                    return
                }
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val path = PathUtil.MUSIC_PATH + fileName
                        var fileOutputStream: FileOutputStream? = null
                        try {
                            fileOutputStream =
                                FileOutputStream(FileManager.instance!!.createNewFile(path))
                            fileOutputStream.write(data.result?.toByteArray()!!)
                            fileOutputStream.flush() //将内容一次性写入文件
                        } catch (e: IOException) {
                            Log.d(TAG, "run() returned: $e")
                            e.printStackTrace()
                        } finally {
                            fileOutputStream?.close()
                        }
                    }

                }
            }
        }

    companion object {
        private const val TAG = "CloudDiskViewModel"
        private var fileName: String? = null
    }

}