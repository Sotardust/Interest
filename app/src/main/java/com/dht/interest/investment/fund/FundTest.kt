package com.dht.interest.investment.fund

import com.google.gson.Gson
import jxl.Workbook
import jxl.write.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt


/**
 *  四种方法
 *
 *  1、定投 每月1000，一年12000
 *  2、初始投资1000
 *  3、一星期 1000 卖一次 收益率达到5%就卖
 *  4、逢低买入，逢高卖出
 *  5、每天投入两百 一周后 ，取出两百
 *
 *  created by Administrator on 2020/3/2 14:19
 */
class FundTest {

    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    private val fundHashMap = HashMap<String, String>()
    private lateinit var workbook: WritableWorkbook

    private fun initData() {
        workbook = Workbook.createWorkbook(File("assets/dataexcel.xls"))
        fundHashMap["银河创新成长混合"] = "assets/银河创新成长混合.json"
        fundHashMap["天弘中证银行指数A"] = "assets/天弘中证银行指数A.json"
        fundHashMap["天弘中证银行指数C"] = "assets/天弘中证银行指数C.json"
        fundHashMap["汇添富中证新能源汽车A"] = "assets/汇添富中证新能源汽车A.json"
        fundHashMap["汇添富中证新能源汽车C"] = "assets/汇添富中证新能源汽车C.json"
        fundHashMap["永赢创业板指数C"] = "assets/永赢创业板指数C.json"
        fundHashMap["天弘中证计算机主题指数C"] = "assets/天弘中证计算机主题指数C.json"
        fundHashMap["天弘中证银行指数A"] = "assets/天弘中证银行指数A.json"
        fundHashMap["工银医药健康股票C"] = "assets/工银医药健康股票C.json"
        fundHashMap["中信建投智信物联网A"] = "assets/中信建投智信物联网A.json"
        fundHashMap["广发多元新兴股票"] = "assets/广发多元新兴股票.json"
        fundHashMap["招商中证白酒指数分级"] = "assets/招商中证白酒指数分级.json"
        fundHashMap["南方500信息联接A"] = "assets/南方500信息联接A.json"

//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
//        fundHashMap["天弘中证银行指数C"] = "assets/tianhongzhongzhengyinhangzhishuc.json"
    }

    /**
     *读取银河创新成长
     */
    fun execute() {
        initData()
        generateExcel()
    }

    /**
     *  第一种策略：每个月15号定投1000
     */
    private fun oneCase(
        sheet: WritableSheet,
        rows: Int,
        fundBeans: List<FundBean>,
        money: Float
    ): Int {
        var mRows = rows
        /**
         * 打印每一年的收入详情
         */
        fun handleYear(results: List<FundBean>, years: Int, period: Int, money: Float) {
            //份额
            var share = 0f
            var bean: FundBean? = null
            //判断行数是否执行过
            var isExecute = false
            for (i in 1..(12 * period + 1)) {
                val mYear = i / 12
                val mMonth = i % 12
                var currentDate =
                    "${(years + mYear)}-${(if (mMonth == 0) 12 else mMonth)}-01"
                if (currentDate == "2020-12-01") currentDate = "2020-01-01"
                for (j in 1..15) {
                    currentDate = currentDate.substringBeforeLast("-") + "-${14 + j}"
                    bean =
                        results.find { format.parse(it.FSRQ!!)!!.time == format.parse(currentDate)!!.time }
                    if (bean != null) break
                }
//                if (bean != null && !(bean.FHFCZ?.isBlank()!!)) {
//                    share = share.times(bean.FHFCZ!!.toFloat())
//                    println("share = ${share},bean  =$bean")
//                }
                if (bean == null) return
                val result: Float = money.div(bean.DWJZ?.toFloat()!!)
                if (i <= 12 * period) {
                    share += result
                } else {
//                    println("currentDate = $currentDate, bean  =$bean")
                }
                if (!isExecute) {
                    mRows++
                    isExecute = true
                }
            }

            val amount = share * bean?.DWJZ?.toFloat()!!
            val mMoney = money * 12 * period
//            addCell(
//                sheet, mRows,
//                "$years-01开始 定投：$period 年",
//                "$mMoney 元",
//                "${formatData(amount, false)}元",
//                "${formatData(amount - mMoney, false)}元",
//                "${formatData((amount - mMoney).div(mMoney) * 100, true)}%",
//                ""
//            )
            print("$years-01开始 定投：$period 年,")
            println(
                "本金：${mMoney}元,"
                        + "总金额：$amount,"
                        + "持有收益：${amount - mMoney},"
                        + "收益率：${(amount - mMoney).div(mMoney) * 100}%"
            )

        }

        println("***************************** 第一种策略 *****************************")
        println("********                                                    *********")
        println("********                 每个月15号定投1000元                  ********")
        println("********                                                    *********")
        println("*********************************************************************")

        //初始年限
        val initYears = 2020
        //遍历每年定投金额收益
//        for (i in 1..20) {
//            val year = initYears - i
//            val date = "$year-01-01"
//            handleYear(
//                fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(date)!!.time },
//                year, 1, money
//            )
//        }
//        mRows++
//        println("--------------------------------------------------------------------")
//        //定投年限
//        for (i in 1..20) {
//            val year = initYears - i
//            val date = "$year-01-01"
//            handleYear(
//                fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(date)!!.time },
//                year, i, money
//            )
//        }

        return mRows

    }


    /**
     *
     * 第二种策略：短线操作每隔7天卖出并买入（以卖出的金额为买入额），
     * 若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止
     */
    private fun twoCase(sheet: WritableSheet, rows: Int, fundBeans: List<FundBean>, money: Float) {
        var mRows = rows
        /**
         * @param years  开始时间 eg 2017/1/1
         * @param period 投资年限 1、2、3年
         * @param mMoney 初始投资金额 12000元
         * @param rate 计划收益率
         */
        fun handleCase(years: Int, period: Int, mMoney: Float, rate: Float) {
            // 以2017年开始为例
            var recordCurrentTime = format.parse("$years-01-01")!!.time
            val results =
                fundBeans.filter { format.parse(it.FSRQ!!)!!.time >= recordCurrentTime }
            //投资金额
            var amount = mMoney
            //份额
            var share = 0f
            //收益率集合
            val list: MutableList<Float> = ArrayList()
            //是否是买入
            var isInit = true
            //买入开始时间
            var startTime = 0L
            //1天对应的毫秒数
            val oneDay = 24 * 60 * 60 * 1000L
            //7天对应的毫秒数
            val sevenDay = 7 * oneDay
            //判断今年是否开始投资
            var isInvest = false
            //判断行数是否执行过
            var isExecute = false
            for (i in 1..(365 * period)) {
                val bean = results.find { it.FSRQ == format.format(recordCurrentTime) }
                if (bean != null) {
                    if (isInit) {
                        share = amount.div(bean.DWJZ!!.toFloat())
                        startTime = recordCurrentTime
                    }
                    isInit = false
                    list.add(if (bean.JZZZL.isNullOrBlank()) 0f else bean.JZZZL!!.toFloat())
//                    println("share = $share ,amount =$amount，时间：${(recordCurrentTime - startTime).div(oneDay)}天，收益率 =${list.sum()} ,bean = ${bean}")
                    if (recordCurrentTime - startTime > sevenDay && list.sum() > rate) {
                        isInit = true
                        amount = share.times(bean.DWJZ!!.toFloat())
                    }
                    isInvest = true
                    if (!isExecute) {
                        mRows++
                        isExecute = true
                    }
                }
                if (i > 90 && !isInvest) {
                    return
                }
                recordCurrentTime += oneDay

            }
            if (!isInvest) return

            addCell(
                sheet, mRows,
                "$years-01开始 投资：$period 年",
                "$mMoney 元",
                "${formatData(amount, false)}元",
                "${formatData(amount - mMoney, false)}元",
                "${formatData((amount - mMoney).div(mMoney) * 100, true)}%",
                "$rate%"
            )
            print("$years-01开始 投资：$period 年")
            println(
                ",本金：${mMoney}元,"
                        + "总金额：${amount.roundToInt()},"
                        + "持有收益：${amount - mMoney},"
                        + "收益率：${(amount - mMoney).div(mMoney) * 100}%,"
                        + "计划收益率：${rate}%"
            )
        }

        println("***************************** 第二种策略 ****************************")
        println("***                                                           ******")
        println("***       短线操作每隔7天卖出并买入（以卖出的金额为买入额）         ******")
        println("***   若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止    ******")
        println("***                                                           ******")
        println("********************************************************************")

        //初始年限
        val initYears = 2020
        //默认计划收益率为5
        val defaultRate = 5f
        //投资一年时间收益
//        for (i in 1..20) {
//            val years = initYears - i
//            handleCase(years, 1, money, defaultRate)
//        }


        //改变收益率
        for (rate in 3..23) {
            mRows++
            for (i in 1..20) {
                val years = initYears - i
                handleCase(years, 1, money, rate.toFloat())
            }

        }

        println("--------------------------------------------------------------------")

        //投资n年时间收益
//        for (i in 1..20) {
//            val years = initYears - i
//            handleCase(years, i, money * i, defaultRate)
//        }

        //改变收益率
//        for (rate in 3..10) {
//            mRows++
//            for (i in 1..20) {
//                val years = initYears - i
//                handleCase(years, i, money * i, rate.toFloat())
//            }
//        }
    }


    /**
     * 逢高卖出7天 逢低买入
     */
    private fun threeCase(fundBeans: List<FundBean>) {
        fun handleCase(time: Long, times: Int, year: String, value: Float): Float {
            val results =
                fundBeans.filter {
                    format.parse(it.FSRQ!!)!!.time > format.parse(year)!!.time
                }
            results.forEach {
                it
                println("bean = ${it}")
            }
            //总金额
            var totalAmount = 0f
            //总份额
            var totalShare = 0f
            // 以2017年开始为例
            var recordTime = time
            //投资金额
//            var initAmount = 1000f
            var amount = 200f
            //份额
            var share = 0f
            //收益率
            val rate = 6f
            //收益率集合
            val list: MutableList<Float> = ArrayList()
            //是否是买入
            var isInit = true
            //买入开始时间
            var startTime = 0L
            //1天对应的秒数
            val oneDay = 24 * 60 * 60 * 1000L
            //7天对应的秒数
            val sevenDay = 7 * oneDay
            for (i in 1..(365 * times)) {
                val bean = results.find { it.FSRQ == format.format(recordTime) }
                if (bean != null) {
                    if (isInit) {
                        share = amount.div(bean.DWJZ!!.toFloat())
                        startTime = recordTime
                        totalShare += share
                    }
                    isInit = false
                    list.add(if (bean.JZZZL.isNullOrBlank()) 0f else bean.JZZZL!!.toFloat())
                    if (recordTime - startTime > sevenDay && list.sum() > rate) {
                        isInit = true
                        amount = share.times(bean.DWJZ!!.toFloat())
                    }

                    if ((if (bean.JZZZL.isNullOrBlank()) 0f else bean.JZZZL!!.toFloat()) < 0f) {
                        share = amount.div(bean.DWJZ!!.toFloat())
                        totalAmount += amount
                        totalShare += share
                    }
                }
                recordTime += oneDay
            }
            return amount
        }

    }

    /**
     * 读取文本信息生成表格数据
     */
    private fun generateExcel() {
        //初始投资金额
        val money = 1000f
        var index = 0
        val titles = arrayListOf("投资计划", "本金", "总金额", "持有收益", "收益率", "计划收益率")
        for ((name, fileName) in fundHashMap) {
            val model = Gson().fromJson(File(fileName).readText(), FundDataModel::class.java)
            val fundBeans = model.Data?.beans!!
            val sheet = workbook.createSheet(name, index++)
            setColumnView(sheet)
            for (i in titles.indices) {
                val label = Label(i, 0, titles[i], getFormat(18))
                sheet.addCell(label)
            }
//            val row = oneCase(sheet, 0, fundBeans, money)
            twoCase(sheet,  1, fundBeans, money )
        }
        workbook.write()
        workbook.close()
    }

    /**
     * 获取单元格格式
     */
    private fun getFormat(size: Int): WritableCellFormat {
        @Suppress("INACCESSIBLE_TYPE") val font = WritableFont(
            WritableFont.TIMES,
            size,
            if (size == 20) WritableFont.BOLD else WritableFont.NO_BOLD
        )
        val format = WritableCellFormat(font)
        // 左右居中
        format.alignment = jxl.format.Alignment.CENTRE
        // 上下居中
        @Suppress("DEPRECATION")
        format.verticalAlignment = VerticalAlignment.CENTRE
        return format
    }

    /**
     * 设置列宽
     */
    private fun setColumnView(sheet: WritableSheet) {
        for (i in 0..5) {
            sheet.setColumnView(i, if (i == 0) 30 else 20)
        }
    }

    /**
     * 添加表格
     */
    private fun addCell(
        sheet: WritableSheet,
        mRows: Int,
        vararg values: String
    ) {
        val format = getFormat(13)
        for (i in 0..5) {
            val label = Label(i, mRows, values[i], format)
            sheet.addCell(label)
        }
    }

    /**
     * 格式化float型数据
     */
    private fun formatData(value: Float, isTwo: Boolean): String {
        return String.format("%.${if (isTwo) 2 else 1}f", value)
    }
}

