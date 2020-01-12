package com.dht.music.local

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.database.bean.music.MusicBean
import com.dht.music.api.MusicApi
import com.dht.music.repository.MusicRepository
import com.dht.music.repository.RecentPlayRepository
import com.dht.network.BaseModel
import com.dht.network.NetworkCallback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URLEncoder
import java.util.*

/**
 * @author Administrator
 */
class LocalViewModel(application: Application) :
    BaseAndroidViewModel(application) {
    private val musicData = MutableLiveData<List<MusicBean>>()
    private val musicApi: MusicApi = MusicApi()
    private val musicRepository: MusicRepository = MusicRepository(application)
    private val playRepository: RecentPlayRepository = RecentPlayRepository(application)

    fun getMusicData(): MutableLiveData<List<MusicBean>> {
        musicRepository.getAllMusics(object :
            LocalCallback<List<MusicBean>>() {
            override fun onChangeData(data: List<MusicBean>?) {
                musicData.postValue(data ?: ArrayList())
            }
        })
        return musicData
    }

    /**
     * 插入或者更新播放历史记录
     *
     * @param musicBean MusicBean
     */
    fun insertOrUpdate(musicBean: MusicBean?) {
        playRepository.insertOrUpdate(musicBean!!)
    }

    /**
     * 上传音乐文件
     *
     * @param fileList        文件集合
     * @param networkCallback 回调接口
     */
    fun uploadFiles(
        fileList: List<File>,
        networkCallback: NetworkCallback<BaseModel<ArrayList<String>>>
    ) {
        val builder = MultipartBody.Builder()
        for (file in fileList) {
            val requestBody = file.asRequestBody("multiple/form-data".toMediaTypeOrNull())
            builder.addFormDataPart("file", URLEncoder.encode(file.name), requestBody)
        }
        builder.setType(MultipartBody.FORM)
        musicApi.uploadFile(builder.build(), networkCallback)
    }
}