package com.dht.baselib.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * GridLayoutManager 分割线
 *
 *
 * created by dht on 2018/7/4 10:30
 */
class HorizontalDecoration(private val height: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = height
        outRect.top = height
    }

}