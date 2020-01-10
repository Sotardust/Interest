package com.dht.music.playmusic

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.database.bean.music.MusicBean
import java.io.IOException

/**
 * @author Administrator
 */
class PlayMusicViewModel(@NonNull application: Application?) :
    BaseAndroidViewModel(application!!) {
    private val mediaPlayer = MediaPlayer()
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun playMusic(music: MusicBean) {
        try {
            mediaPlayer.setDataSource(music.path)
            val attrBuilder = AudioAttributes.Builder()
            attrBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            mediaPlayer.setAudioAttributes(attrBuilder.build())
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { mp ->
                Log.d(TAG, "onPrepared: ")
                mp.start()
            }
            //           Observable.interval(0,500,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
//                   .observeOn(AndroidSchedulers.mainThread())
//                   .subscribe(new Observer<Long>() {
//                       @Override
//                       public void onSubscribe(Disposable d) {
//
//                       }
//
//                       @Override
//                       public void onNext(Long aLong) {
//                           Log.d(TAG, "onNext: position = "+ mediaPlayer.getCurrentPosition());
//                           Log.d(TAG, "onNext: "+ aLong);
//                       }
//
//                       @Override
//                       public void onError(Throwable e) {
//
//                       }
//
//                       @Override
//                       public void onComplete() {
//
//                       }
//                   });
//            mediaPlayer.start();
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    companion object {
        private const val TAG = "PlayMusicViewModel"
    }
}