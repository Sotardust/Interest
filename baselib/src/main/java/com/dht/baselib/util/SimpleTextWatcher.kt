package com.dht.baselib.util

import android.text.Editable
import android.text.TextWatcher

/**
 * created by dht on 2019/1/16 15:21
 */
open class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}
}