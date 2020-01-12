package com.dht.interest.music

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.R
import com.dht.music.repository.CloudDiskRepository
import com.dht.music.repository.MusicRepository
import com.dht.music.repository.RecentPlayRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Administrator
 */
class MusicViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val repository: MusicRepository = MusicRepository(application)
    private val recentPlayRepository: RecentPlayRepository = RecentPlayRepository(application)
    private val cloudDiskRepository: CloudDiskRepository = CloudDiskRepository(application)
    private val list: MutableList<Int> = ArrayList()
    private val musicData: MutableLiveData<List<Int>> = MutableLiveData()
    /**
     * 获取music页item对应个数
     *
     * @return MutableLiveData
     */
    val endListData: MutableLiveData<List<Int>>
        get() {
            repository.getMusicTotal(object : LocalCallback<Int>() {
                override fun onChangeData(data: Int?) {
                    list[0] = data!!
                    musicData.postValue(list)
                }
            })
            recentPlayRepository.getPlayTotal(object : LocalCallback<Int?>() {
                override fun onChangeData(data: Int?) {
                    super.onChangeData(data)
                    list[1] = data!!
                    musicData.postValue(list)
                }
            })
            GlobalScope.launch {

                list[2] = cloudDiskRepository.getTotal()
                musicData.postValue(list)
            }

            return musicData
        }

    companion object {
        private const val TAG = "MusicViewModel"
    }

    init {
        val strings =
            application.resources.getStringArray(R.array.musicList)
        for (i in strings.indices) {
            list.add(0)
        }
    }
}