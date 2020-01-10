package com.dht.interest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.dht.interest.util.ScreenUtil

/**
 * @author dai
 * @date 2018/1/10
 */
class ProgressView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {

    private var viewWidth = 0
    private var viewHeight = 0
    private var paint: Paint? = null
    /**
     * 自定义View默认的宽高
     */
    private var defaultSize = 0
    /**
     * 圆弧进度条宽度
     */
    private val barWidth = 5f

    private fun init(
        context: Context,
        attrs: AttributeSet
    ) {
        paint = Paint()
        //只描边，不填充
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = 3f
        defaultSize = dip2px(context, ScreenUtil.width.toFloat())
    }


    private var min = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = measureSize(defaultSize, heightMeasureSpec)
        viewWidth = measureSize(defaultSize, widthMeasureSpec)
        // 获取View最短边的长度
        min = Math.min(width, height)
        // 强制改View为以最短边为长度的正方形
        setMeasuredDimension(min, min)
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
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

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.rotate(-30f, min / 2.toFloat(), height / 2.toFloat())
        for (i in 0..11) {
            paint!!.color = colors[i]
            canvas.drawLine(
                min / 2.toFloat(),
                height / 2 + 20.toFloat(),
                min / 2.toFloat(),
                height / 2 + 40.toFloat(),
                paint!!
            )
            canvas.rotate(-30f, min / 2.toFloat(), height / 2.toFloat())
        }
        changeColors()
        invalidate()
    }

    var colors = intArrayOf(
        -1,
        -657931,
        -1315861,
        -1973791,
        -2631721,
        -3289651,
        -3881788,
        -4539718,
        -5197648,
        -5855578,
        -6513508,
        -7829368
    )

    private fun changeColors() {
        val end = colors[colors.size - 1]
        val temp = 0
        for (i in colors.size - 1 downTo 1) {
            colors[i] = colors[i - 1]
            if (i == 1) {
                colors[0] = end
            }
        }
    }

    init {
        println("ProgressView.ProgressView1")
        init(context, attrs)
    }
}