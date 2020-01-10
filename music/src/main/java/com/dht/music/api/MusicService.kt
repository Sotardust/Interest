package com.dht.music.api

import com.dht.database.bean.music.CloudMusicBean
import com.dht.network.BaseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * @author Administrator
 */
interface MusicService {
    /**
     * 上传音乐文件
     *
     * @param body MultipartBody
     */
    @POST("uploadMusic")
    fun uploadFile(@Body body: MultipartBody): Call<BaseModel<ArrayList<String>>>

    /**
     * 获取服务端云盘音乐列表数据
     *
     * @return 云盘音乐数据集合
     */
    @get:GET("getCloudMusics")
    val cloudMusicList: Call<BaseModel<List<CloudMusicBean>>>

    /**
     * 下载音乐文件
     *
     * @param songName 歌曲名称
     * @return 文件以以字符类型返回
     */
    @FormUrlEncoded
    @POST("downloadMusic")
    fun downloadMusic(@Field("songName") songName: String): Call<BaseModel<String>>
}