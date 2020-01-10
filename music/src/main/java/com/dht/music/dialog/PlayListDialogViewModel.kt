package com.dht.music.dialog

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.database.bean.music.MusicBean
import com.dht.music.repository.MusicRepository

/**
 * @author Administrator
 */
class PlayListDialogViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val musicData: MutableLiveData<List<MusicBean>> = MutableLiveData()
    private val musicRepository: MusicRepository = MusicRepository(application)
    fun getMusicData(): MutableLiveData<List<MusicBean>> {
        musicRepository.getAllMusics(object : LocalCallback<List<MusicBean>>() {
            override fun onChangeData(data: List<MusicBean>?) {
                super.onChangeData(data)
                musicData.postValue(data)
            }
        })
        return musicData
    }

}