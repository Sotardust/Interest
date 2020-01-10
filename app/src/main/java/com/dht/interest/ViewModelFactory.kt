package com.dht.interest

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dht.interest.other.SecondaryLinkageViewModel
import com.dht.interest.phone.allcalls.AllCallsViewModel

/**
 * 创建工厂传参
 *
 *
 * created by dht on 2018/8/10 11:23
 *
 * @author Administrator
 */
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecondaryLinkageViewModel::class.java)) {
            return SecondaryLinkageViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AllCallsViewModel::class.java)) {
            return AllCallsViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(MessageApplication.application)
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}