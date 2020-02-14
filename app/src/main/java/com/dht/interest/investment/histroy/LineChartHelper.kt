package com.dht.interest.investment.histroy

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.dht.interest.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 封装LinChart 操作方法
 *
 *
 * created by dht on 2019/6/4 19:08
 */
class LineChartHelper private constructor() {

    private object Holder {
        val helper = LineChartHelper()
    }

    /**
     * 初始化线状图显示两块的Chart
     *
     * @param lineChart LineChart
     * @param listener  监听器
     */
    fun initLineChart(
        lineChart: LineChart,
        listener: OnChartValueSelectedListener? = null
    ) {
        // background color
        lineChart.setBackgroundColor(Color.WHITE)

        // disable description text
        lineChart.description.isEnabled = true

        // enable touch gestures
        lineChart.setTouchEnabled(true)
        // set listeners
        lineChart.setOnChartValueSelectedListener(listener)
        lineChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
        //        MyMarkerView mv = new MyMarkerView(context, R.layout.view_marker_view);
        //
        //        // Set the marker to the lineChart
        //        mv.setChartView(lineChart);
        //        lineChart.setMarker(mv);

        // 是否可以滑动拖拽
        lineChart.isDragEnabled = true
        // 设置x轴和y轴能否同时缩放。默认是否
        lineChart.setPinchZoom(false)
        //是否可以缩放 仅x轴
        lineChart.isScaleXEnabled = true
        lineChart.setDrawBorders(false)

        // get the legend (only possible after setting data)
        val l = lineChart.legend
        // draw legend entries as lines
        l.form = Legend.LegendForm.NONE
        //设置X坐标
        setXAxis(lineChart)
        //设置Y坐标
        setYAxis(lineChart)

        lineChart.animateX(1000)

        lineChart.setDragOffsetX(20f)

    }

    private fun setXAxis(lineChart: LineChart) {
        val xAxis = lineChart.xAxis

        //设置X轴格式化数据
//        xAxis.valueFormatter = IndexAxisValueFormatter(list)

        //标签文本旋转角度
        xAxis.labelRotationAngle = -60f
        //设置X轴线
        xAxis.setDrawAxisLine(true)

        xAxis.setDrawGridLinesBehindData(true)
        //禁止绘制先
        xAxis.setDrawGridLines(false)
        //使用标签
        xAxis.setDrawLabels(true)

        xAxis.granularity = 1f
        //设置位置底部
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setAvoidFirstLastClipping(true)

        // vertical grid lines
        //        xAxis.enableGridDashedLine(10f, 10f, 0f);


    }

    private fun setYAxis(lineChart: LineChart) {

        val yAxis = lineChart.axisLeft
        //禁用右边轴线
        lineChart.axisRight.isEnabled = false
        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setDrawZeroLine(true)
        yAxis.zeroLineWidth = 0.5f
        // axis range
        yAxis.axisMaximum = 30f
        yAxis.axisMinimum = 0f

    }


    fun setChartData(
        lineChart: LineChart?,
        context: Context?,
        beans: List<HistroyBean>,
        isPrice: Boolean,
        isVolume: Boolean,
        isYtm: Boolean,
        isPrem: Boolean,
        isValue: Boolean
    ) {

        if (lineChart == null || context == null) return
        lineChart.zoomToCenter(0f, 1f)
        val length = beans.size
        if (length > 7) {
            val numberFormat = NumberFormat.getInstance()
            numberFormat.maximumFractionDigits = 1
            numberFormat.format((length / 7f).toDouble())
            //X轴放大倍数
            lineChart.zoomToCenter(
                (numberFormat.format((length.toFloat() / 7f).toDouble())).toFloat(),
                1f
            )
        }
        val values = ArrayList<Entry>()
        val list = beans.map {
            it.last_chg_dt.substringAfter("-")
        }
        for (i in beans.indices) {
            try {
                when {
                    isPrice -> {
                        values.add(Entry(i.toFloat(), beans[i].price.toFloat()))
                    }
                    isVolume -> {
                        values.add(Entry(i.toFloat(), beans[i].volume.toFloat()))
                    }
                    isYtm -> {
                        values.add(Entry(i.toFloat(), beans[i].ytm_rt.toFloat()))
                    }
                    isPrem -> {
                        values.add(Entry(i.toFloat(), beans[i].premium_rt.toFloat()))
                    }
                    isValue -> {
                        values.add(Entry(i.toFloat(), beans[i].convert_value.toFloat()))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setAxisData(lineChart, beans, list)
        val set1 = getDataSet(lineChart, values, context, R.color.color_0091FF)

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(context, R.drawable.bg_gradient_line_chart)
            set1.fillDrawable = drawable
        }

        val dataSets = ArrayList<ILineDataSet>()

        dataSets.add(set1)
        set1.notifyDataSetChanged()
        val data = LineData(dataSets)
        lineChart.data = data
        lineChart.data.notifyDataChanged()
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()
    }


    private fun getDataSet(
        lineChart: LineChart,
        values: List<Entry>,
        context: Context, @ColorRes resId: Int
    ): LineDataSet {
        val set1 = LineDataSet(values, null)
        set1.setDrawIcons(false)
        // draw dashed line
        //        set1.enableDashedLine(10f, 2f, 0f);
        //设置顶部文字
        set1.setDrawValues(true)
        set1.valueTextSize = 10f
        set1.color = context.resources.getColor(resId)
        //        set1.setCircleColor(context.getResources().getColor(R.color.chart_line));

        // line thickness and point size
        set1.lineWidth = 0.5f
        // set1.setHighLightColor(Color.YELLOW);
        //        set1.setHighlightEnabled(false);
        set1.circleRadius = 1f
        set1.setDrawCircles(true)
        // draw points as solid circles
        set1.setDrawCircleHole(false)

        //customize legend entry
//        set1.formLineWidth = 1f
//        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 1f), 0f)
//        set1.formSize = 5f

        // draw selection line as dashed
        //            set1.enableDashedHighlightLine(10f, 5f, 0f);

        set1.mode = LineDataSet.Mode.LINEAR

        // set the filled area
        set1.setDrawFilled(false)
        set1.fillFormatter = IFillFormatter { _, _ -> lineChart.axisLeft.axisMinimum }

        return set1
    }

    /**
     * 日期转换
     *
     * @param value 日期
     * @return 日期
     */
    private fun getDate(value: Long): String {
        val format1 = SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
        return format1.format(value)
    }

    /**
     * 设置X轴 Y轴数据
     *
     * @param lineChart LineChart
     * @param beans     历史集合
     * @param list      X轴展示的数据集合
     */
    private fun setAxisData(lineChart: LineChart, beans: List<HistroyBean>, list: List<String>) {
        if (beans.isNotEmpty()) {
            val yAxis = lineChart.axisLeft
            val max = beans.map { it.price.toFloat() }.max()
            // axis range
            yAxis.axisMaximum = getFloatValue(max!!, true)
            yAxis.axisMinimum = 0f
            val xAxis = lineChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(list)
        }

    }

    /**
     * 设置最大值  1/6 或者最小范围的1/3
     *
     * @param value  最大值以及参考值下限
     * @param isHigh 是否是上限
     * @return float
     */
    private fun getFloatValue(value: Float, isHigh: Boolean): Float {
        val numberFormat = NumberFormat.getInstance()
        val string = value.toString()
        // 设置精确到小数点后2位
        var digits = 0
        if (string.contains(".")) {
            try {
                val strings =
                    string.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                digits = strings[1].length
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        numberFormat.maximumFractionDigits = digits
        val result = numberFormat.format((value / if (isHigh) 6f else 3f).toDouble()).toFloat()
        return if (isHigh) value + result else value - result
    }

    companion object {
        val INSTANCE: LineChartHelper
            get() = Holder.helper
    }
}
