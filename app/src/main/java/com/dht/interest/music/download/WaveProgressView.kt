package com.dht.interest.music.download

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.TextView
import androidx.annotation.Nullable
import com.dht.interest.R

/**
 * Created by dai on 2018/1/11.
 */
class WaveProgressView(context: Context, @Nullable attrs: AttributeSet) :
    View(context, attrs) {
    private var wavePaint //绘制波浪画笔
            : Paint? = null
    private var wavePath //绘制波浪Path
            : Path? = null
    private var waveWidth = 0f //波浪宽度 = 0f
    private var waveHeight = 0f//波浪高度 = 0f
    private var waveNum = 0//波浪组的数量（一次起伏为一组） = 0
    private val defaultSize = 0 //自定义View默认的宽高 = 0
    private var maxHeight = 0 //为了看到波浪效果，给定一个比填充物稍高的高度 = 0
    private var viewSize = 0//重新测量后View实际的宽高 = 0
    private var waveProgressAnim: WaveProgressAnim? = null
    private var percent = 0f//进度条占比 = 0f
    private var progressNum = 0f //可以更新的进度条数值 = 0f
    private var maxNum = 0f //进度条最大值 = 0f
    private var waveMovingDistance = 0f //波浪平移的距离 = 0f
    private var circlePaint //圆形进度框画笔
            : Paint? = null
    private var bitmap //缓存bitmap
            : Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private var waveColor = 0 //波浪颜色 = 0
    private var bgColor = 0//背景进度框颜色 = 0
    private var secondWaveColor = 0//第二层波浪颜色 = 0
    private var isDrawSecondWave = false//是否绘制第二层波浪 = false
    private var secondWavePaint: Paint? = null
    private fun init(
        context: Context,
        attrs: AttributeSet
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.WaveProgressView)
        waveWidth = typedArray.getDimension(
            R.styleable.WaveProgressView_wave_width,
            dip2px(context, 15f).toFloat()
        )
        waveHeight = typedArray.getDimension(
            R.styleable.WaveProgressView_wave_height,
            dip2px(context, 5f).toFloat()
        )
        waveColor = typedArray.getColor(
            R.styleable.WaveProgressView_wave_color,
            Color.GREEN
        )
        bgColor =
            typedArray.getColor(R.styleable.WaveProgressView_bg_color, Color.GRAY)
        secondWaveColor = typedArray.getColor(
            R.styleable.WaveProgressView_second_wave_color,
            resources.getColor(R.color.orange)
        )
        typedArray.recycle()
        secondWavePaint = Paint()
        secondWavePaint!!.color = secondWaveColor
        secondWavePaint!!.isAntiAlias = true //设置抗锯齿
        //因为要覆盖在第一层波浪上，且要让半透明生效，所以选SRC_ATOP模式
        secondWavePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        isDrawSecondWave = false
        maxHeight = dip2px(context, 50f)
        percent = 0f
        progressNum = 0f
        maxNum = 100f
        waveMovingDistance = 0f
        waveProgressAnim = WaveProgressAnim()
        wavePath = Path()
        wavePaint = Paint()
        wavePaint!!.color = Color.GREEN
        wavePaint!!.isAntiAlias = true //设置抗锯齿
        waveProgressAnim!!.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {
                Log.d(TAG, "onAnimationRepeat: ")
                if (percent == progressNum / maxNum) {
                    waveProgressAnim!!.duration = 3000
                }
            }
        })
        wavePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) //根据绘制顺序的不同选择相应的模式即可
        wavePaint!!.color = waveColor
        circlePaint = Paint()
        circlePaint!!.isAntiAlias = true //设置抗锯齿
        circlePaint!!.color = bgColor
        textView = TextView(getContext())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //这里用到了缓存技术
        bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(bitmap!!)
        bitmapCanvas!!.drawCircle(
            viewSize / 2.toFloat(),
            viewSize / 2.toFloat(),
            viewSize / 2.toFloat(),
            circlePaint!!
        )
        bitmapCanvas!!.drawPath(getWavePath()!!, wavePaint!!)
        if (isDrawSecondWave) {
            bitmapCanvas!!.drawPath(secondWavePath!!, secondWavePaint!!)
        }
        canvas.drawBitmap(bitmap!!, 0f, 0f, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureSize(defaultSize, widthMeasureSpec)
        val height = measureSize(defaultSize, heightMeasureSpec)
        val min = Math.min(width, height) // 获取View最短边的长度
        setMeasuredDimension(min, min) // 强制改View为以最短边为长度的正方形
        viewSize = min
        waveNum = Math.ceil((viewSize / waveWidth / 3).toString().toDouble()).toInt()
        println("waveNum = $waveNum")
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

    private fun getWavePath(): Path? {
        wavePath!!.reset()
        //移动到右上方，也就是p0点
        wavePath!!.moveTo(
            viewSize.toFloat(),
            (1 - percent) * viewSize
        ) //让p0p1的长度随percent的增加而增加（注意这里y轴方向默认是向下的）
        //移动到右下方，也就是p1点
        wavePath!!.lineTo(viewSize.toFloat(), viewSize.toFloat())
        //移动到左下边，也就是p2点
        wavePath!!.lineTo(0f, viewSize.toFloat())
        //移动到左上方，也就是p3点
//        wavePath.lineTo(0, (1-percent)*viewSize);//让p3p2的长度随percent的增加而增加（注意这里y轴方向默认是向下的）
//移动到左上方，也就是p3点（x轴默认方向是向右的，我们要向左平移，因此设为负值）
//wavePath.lineTo(0, (1-percent)*viewSize);
        wavePath!!.lineTo(-waveMovingDistance, (1 - percent) * viewSize)
        var changeWaveHeight = waveHeight
        if (onAnimationListener != null) {
            changeWaveHeight = if (onAnimationListener!!.howToChangeWaveHeight(
                    percent,
                    waveHeight
                ) == 0f && percent < 1
            ) waveHeight else onAnimationListener!!.howToChangeWaveHeight(percent, waveHeight)
        }
        //从p3开始向p0方向绘制波浪曲线
        for (i in 0 until waveNum * 2) {
            wavePath!!.rQuadTo(waveWidth / 2, changeWaveHeight, waveWidth, 0f)
            wavePath!!.rQuadTo(waveWidth / 2, -changeWaveHeight, waveWidth, 0f)
        }
        //将path封闭起来
        wavePath!!.close()
        return wavePath
    }

    private var textView: TextView? = null
    private var onAnimationListener: OnAnimationListener? = null

    inner class WaveProgressAnim : Animation() {
        //省略部分代码...
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            super.applyTransformation(interpolatedTime, t)
            if (percent < progressNum / maxNum) {
                percent = interpolatedTime * progressNum / maxNum
                if (textView != null && onAnimationListener != null) {
                    textView!!.text = onAnimationListener!!.howToChangeText(
                        interpolatedTime,
                        progressNum,
                        maxNum
                    )
                }
            }
            waveMovingDistance = interpolatedTime * waveNum * waveWidth * 2
            postInvalidate()
        }
    }

    /**
     * 设置显示文字的TextView
     *
     * @param textView
     */
    fun setTextView(textView: TextView?) {
        this.textView = textView
    }

    interface OnAnimationListener {
        /**
         * 如何处理要显示的文字内容
         *
         * @param interpolatedTime 从0渐变成1,到1时结束动画
         * @param updateNum        进度条数值
         * @param maxNum           进度条最大值
         * @return
         */
        fun howToChangeText(
            interpolatedTime: Float,
            updateNum: Float,
            maxNum: Float
        ): String?

        /**
         * 如何处理波浪高度
         *
         * @param percent    进度占比
         * @param waveHeight 波浪高度
         * @return
         */
        fun howToChangeWaveHeight(
            percent: Float,
            waveHeight: Float
        ): Float
    }

    fun setOnAnimationListener(onAnimationListener: OnAnimationListener?) {
        this.onAnimationListener = onAnimationListener
    }

    /**
     * 设置进度条数值
     *
     * @param progressNum 进度条数值
     * @param time        动画持续时间
     */
    fun setProgressNum(progressNum: Float, time: Int) {
        this.progressNum = progressNum
        percent = 0f
        waveProgressAnim!!.duration = time.toLong()
        startAnimation(waveProgressAnim)
        waveProgressAnim!!.repeatCount = Animation.INFINITE //让动画无限循环
        waveProgressAnim!!.interpolator = LinearInterpolator() //让动画匀速播放，不然会出现波浪平移停顿的现象
    }

    //移动到左上方，也就是p3点
    //移动到左下方，也就是p2点
    //移动到右下方，也就是p1点
    //移动到右上方，也就是p0点
    //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
    //将path封闭起来
    private val secondWavePath: Path?
        private get() {
            var changeWaveHeight = waveHeight
            if (onAnimationListener != null) {
                changeWaveHeight = if (onAnimationListener!!.howToChangeWaveHeight(
                        percent,
                        waveHeight
                    ) == 0f && percent < 1
                ) waveHeight else onAnimationListener!!.howToChangeWaveHeight(percent, waveHeight)
            }
            wavePath!!.reset()
            //移动到左上方，也就是p3点
            wavePath!!.moveTo(0f, (1 - percent) * viewSize)
            //移动到左下方，也就是p2点
            wavePath!!.lineTo(0f, viewSize.toFloat())
            //移动到右下方，也就是p1点
            wavePath!!.lineTo(viewSize.toFloat(), viewSize.toFloat())
            //移动到右上方，也就是p0点
            wavePath!!.lineTo(viewSize + waveMovingDistance, (1 - percent) * viewSize)
            //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
            for (i in 0 until waveNum * 2) {
                wavePath!!.rQuadTo(-waveWidth / 2, changeWaveHeight, -waveWidth, 0f)
                wavePath!!.rQuadTo(-waveWidth / 2, -changeWaveHeight, -waveWidth, 0f)
            }
            //将path封闭起来
            wavePath!!.close()
            return wavePath
        }

    /**
     * 是否绘制第二层波浪
     *
     * @param isDrawSecondWave
     */
    fun setDrawSecondWave(isDrawSecondWave: Boolean) {
        this.isDrawSecondWave = isDrawSecondWave
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    companion object {
        private const val TAG = "dht"
    }

    init {
        init(context, attrs)
    }
}