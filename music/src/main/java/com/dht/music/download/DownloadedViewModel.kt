package com.dht.music.download

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData

import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.database.bean.music.MusicBean
import com.dht.music.repository.RecentPlayRepository

class DownloadedViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private var musicData: MutableLiveData<List<MusicBean>>? = null
    private val recentPlayRepository: RecentPlayRepository = RecentPlayRepository(application)

    val downloadedEntityData: MutableLiveData<List<MusicBean>>?
        get() {
            if (musicData == null) {
                musicData = MutableLiveData()
            }
            return musicData
        }

}