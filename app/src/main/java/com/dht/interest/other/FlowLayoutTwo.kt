package com.dht.interest.other

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dht.interest.util.ScreenUtil
import java.util.*

/**
 * created by Administrator on 2018/12/14 15:05
 */
class FlowLayoutTwo : ViewGroup {
    private val viewLists: MutableList<List<View>> =
        ArrayList()
    private val numList: List<Int> = ArrayList()

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = childCount
        Log.d(
            TAG,
            "********************************************onMeasure: count = $count********************************************"
        )
        for (i in 0 until count) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val params = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            val width =
                childWidth + params.leftMargin + params.rightMargin + paddingLeft + paddingRight
            val height =
                childHeight + params.topMargin + params.bottomMargin + paddingTop + paddingBottom
            Log.d(
                TAG,
                "onMeasure: width = " + width + ", childWidth = " + childWidth + ", leftMargin = " + params.leftMargin + ", rightMargin = " + params.rightMargin
                        + ", getPaddingLeft = " + paddingLeft + ", getPaddingRight = " + paddingRight
            )
            Log.d(
                TAG,
                "onMeasure: height = " + height + ", childHeight = " + childHeight + ", topMargin = " + params.topMargin + ", bottomMargin = " + params.bottomMargin
                        + ", getPaddingTop = " + paddingTop + ", getPaddingBottom = " + paddingBottom
            )
            val widthSize = measureSize(childWidth, widthMeasureSpec)
            val heightSize = measureSize(childHeight, heightMeasureSpec)
            setMeasuredDimension(widthSize, heightSize)
        }
    }

    private val lineWidth = 10
    private val lineHeight = 10
    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        Log.d(
            TAG,
            "onLayout() called with: changed = [$changed], l = [$l], t = [$t], r = [$r], b = [$b]"
        )
        val count = childCount
        var totalWidth = 0
        @SuppressLint("DrawAllocation") val viewList: MutableList<View> =
            ArrayList()
        for (i in 0 until count) {
            val child = getChildAt(i)
            val params = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            totalWidth += childWidth + params.leftMargin + params.rightMargin + paddingLeft + paddingRight + lineWidth
            if (totalWidth > ScreenUtil.width) {
                viewLists.add(viewList)
                totalWidth = 0
                viewList.clear()
            }
            viewList.add(child)
            if (i == count - 1 && totalWidth < ScreenUtil.width) {
                viewLists.add(viewList)
            }
            //            int width = childWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight();
//            int height = childHeight + params.topMargin + params.bottomMargin + getPaddingTop() + getPaddingBottom();
//            Log.d(TAG, "onLayout: width = " + width + ", childWidth = " + childWidth + ", leftMargin = " + params.leftMargin + ", rightMargin = " + params.rightMargin
//                    + ", getPaddingLeft = " + getPaddingLeft() + ", getPaddingRight = " + getPaddingRight());
//            Log.d(TAG, "onLayout: height = " + height + ", childHeight = " + childHeight + ", topMargin = " + params.topMargin + ", bottomMargin = " + params.bottomMargin
//                    + ", getPaddingTop = " + getPaddingTop() + ", getPaddingBottom = " + getPaddingBottom());
        }
        var top = lineHeight
        var bottom = lineHeight
        Log.d(TAG, "onLayout: viewLists = $viewLists")
        Log.d(
            TAG,
            "onLayout: viewLists size= " + viewLists.size
        )
        for (i in viewLists.indices) {
            @SuppressLint("DrawAllocation") val list: List<View> =
                ArrayList(viewLists[i])
            Log.d(
                TAG,
                "onLayout: list.size = " + list.size + ", i = " + i
            )
            val view = list[0]
            var left = 0
            var right = 0
            bottom += view.measuredHeight
            for (j in list.indices) {
                val child = list[j]
                val params = child.layoutParams as MarginLayoutParams
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                right += childWidth + params.leftMargin + params.rightMargin + paddingLeft + paddingRight
                Log.d(
                    TAG,
                    "onLayout: left = $left, top = $top, right = $right, bottom = $bottom"
                )
                child.layout(left, top, right, bottom)
                left = right + lineWidth
            }
            top = bottom + lineHeight
        }
    }

    /**
     * 测量、设置View默认宽高
     *
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize)
        }
        return result
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    companion object {
        private const val TAG = "FlowLayoutTwo"
        private const val defaultSize = 200
    }
}