package com.dht.music.recentplay

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData

import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.database.bean.music.MusicBean
import com.dht.database.bean.music.RecentPlayBean
import com.dht.music.recentplay.RecentPlayAdapter.DynamicType
import com.dht.music.repository.RecentPlayRepository

/**
 * @author Administrator
 */
class RecentPlayViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    private val musicData: MutableLiveData<List<RecentPlayBean>> =
        MutableLiveData()
    private val recentPlayRepository: RecentPlayRepository = RecentPlayRepository(application)
    /**
     * 获取最近播放实体类
     *
     * @return musicData
     */
    val recentPlayEntityData: MutableLiveData<List<RecentPlayBean>>
        get() {
            recentPlayRepository.getRecentPlayEntities(object : LocalCallback<List<RecentPlayBean>>() {
                override fun onChangeData(data: List<RecentPlayBean>?) {
                    super.onChangeData(data)
                    musicData.postValue(data)
                }
            })
            return musicData
        }

    /**
     * 插入或更新最近RecentPlayEntity实体类
     */
    fun insertOrUpdate(bean: MusicBean) {
        recentPlayRepository.insertOrUpdate(bean)
    }

    /**
     * 按所有时间升序或降序排列
     */
    val ascRecentAllTime: Unit
        get() {
            recentPlayRepository.getAscRecentAllTime(object :
                LocalCallback<List<RecentPlayBean>>() {
                override fun onChangeData(data: List<RecentPlayBean>?) {
                    super.onChangeData(data)
                    musicData.postValue(data)
                }
            })
        }

    /**
     * 按最近播放时间升序或降序排列
     */
    val ascRecentPlayTime: Unit
        get() {
            recentPlayRepository.getAscRecentPlayTime(object :
                LocalCallback<List<RecentPlayBean>>() {
                override fun onChangeData(data: List<RecentPlayBean>?) {
                    super.onChangeData(data)
                    musicData.postValue(data)
                }
            })
        }

    /**
     * 按最近一周播放次数升序或降序排列
     */
    val ascRecentOneWeek: Unit
        get() {
            recentPlayRepository.getAscRecentOneWeek(object :
                LocalCallback<List<RecentPlayBean>>() {
                override fun onChangeData(data: List<RecentPlayBean>?) {
                    super.onChangeData(data)
                    musicData.postValue(data)
                }
            })
        }

    /**
     * 根据歌曲名称删除对应数据
     *
     * @param songName    歌曲名称
     * @param dynamicType 选择类型
     */
    fun deleteCurrentRecentEntity(
        songName: String?,
        dynamicType: DynamicType?
    ) {
        recentPlayRepository.deleteRecentPlayEntity(
            songName!!,
            object : LocalCallback<String?>() {
                override fun onChangeData() {
                    super.onChangeData()
                    when (dynamicType) {
                        DynamicType.PLAY_TIME -> ascRecentPlayTime
                        DynamicType.PLAY_COUNT -> ascRecentOneWeek
                        DynamicType.PLAY_TOTAL -> ascRecentAllTime
                        else -> {
                        }
                    }
                }
            })
    }

    companion object {
        private const val TAG = "RecentPlayViewModel"
    }

}