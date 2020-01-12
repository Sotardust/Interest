package com.dht.music.api

import com.dht.baselib.util.BASE_URL
import com.dht.baselib.util.loge
import com.dht.database.bean.music.CloudMusicBean
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import com.dht.network.RetrofitClient
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.util.*

/**
 * @author Administrator
 */
class MusicApi {
    /**
     * 上传音乐文件
     *
     * @param body            MultipartBody
     * @param networkCallback 回调接口
     */
    fun uploadFile(
        body: MultipartBody,
        networkCallback: NetworkCallback<BaseModel<ArrayList<String>>>
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            withContext(Dispatchers.IO) {
                val response = RetrofitClient.INSTANCE.create(BASE_URL, MusicService::class.java)
                    ?.uploadFile(body)
                    ?.execute()
                networkCallback.onChangeData(if (response?.code() != 200) null else response.body())
            }
        }
    }

    /**
     * 获取服务端云盘音乐列表数据
     *
     * @param networkCallback NetworkCallback
     */
    fun getMusicList(networkCallback: NetworkCallback<BaseModel<List<CloudMusicBean>>>) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            val response = RetrofitClient.INSTANCE.create(BASE_URL, MusicService::class.java)
                .cloudMusicList.execute()
            withContext(Dispatchers.Main) {
                networkCallback.onChangeData(if (response.code() != 200) null else response.body())
            }
        }
    }

    /**
     * 下载音乐
     *
     * @param songName        歌曲名称
     * @param networkCallback NetworkCallback
     */
    fun downloadMusic(
        songName: String,
        networkCallback: NetworkCallback<BaseModel<String>>
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            networkCallback.onChangeData(null)
            loge(throwable)
        }
        GlobalScope.launch(handler) {
            withContext(Dispatchers.IO) {
                val response = RetrofitClient.INSTANCE.create(BASE_URL, MusicService::class.java)
                    ?.downloadMusic(songName)?.execute()
                networkCallback.onChangeData(if (response?.code() != 200) null else response.body())
            }
        }
    }

    companion object {
        private const val TAG = "MusicApi"
    }
}