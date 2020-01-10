package com.dht.baselib.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * 含有Application上下文的ViewModel
 *
 *
 * created by dht on 2018/6/29 14:48
 */
open class BaseAndroidViewModel : AndroidViewModel {
    constructor(application: Application) : super(application)
}
