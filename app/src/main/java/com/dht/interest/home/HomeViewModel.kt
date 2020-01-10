package com.dht.interest.home

import android.app.Application
import androidx.annotation.NonNull
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.music.repository.MusicRepository
import java.io.File
import java.util.*

/**
 * @author Administrator
 */
class HomeViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val musicRepository: MusicRepository = MusicRepository(application)
    private val filePaths = ArrayList<File>()

    companion object {
        private const val TAG = "HomeViewModel"
    }

}