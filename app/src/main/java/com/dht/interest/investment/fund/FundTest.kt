package com.dht.interest.investment.fund

import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *  四种方法
 *
 *  1、定投 每月1000，一年12000
 *  2、初始投资1000
 *  3、一星期 1000 卖一次 收益率达到5%就卖
 *  4、逢低买入，逢高卖出
 *
 *  created by Administrator on 2020/3/2 14:19
 */
class FundTest {
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    private val year19 = "2018-12-31"
    private val year18 = "2017-12-31"
    private val year17 = "2016-12-31"

    //2017/1/1
    private val time17 = 1483200000000
    private val time18 = 1514736000000
    private val time19 = 1546272000000
    private val time20 = 1577808000000
    /**
     *读取银河创新成长
     */
    fun readYinHe() {
        val fileName = "assets/yinhechuagnxinchengzhang.json"
        val file = File(fileName)
        val model = Gson().fromJson(file.readText(), FundDataModel::class.java)
        val fundBeans = model.Data?.beans!!
        oneCase(fundBeans)
        println("第二种策略：")
        twoCase(fundBeans)
    }


    /**
     *  第一种策略：
     */
    private fun oneCase(fundBeans: List<FundBean>) {

        println("第一种策略：")
        println("定投三年（2017.01~2020.01）：")
        println(
            "总份额为：${14213.88}\n总投入金额：${1000 * 12 * 3}元\n"
                    + "总收益：${65359.6844}\n"
                    + "总盈利：${29359.68}\n"
                    + "收益率：${81.55}%\n"
        )
        println("定投二年（2018.01~2020.01）：")
        println(
            "总份额为：${9225.87}\n总投入金额：${1000 * 12 * 2}元\n"
                    + "总收益：${42423.32}\n"
                    + "总盈利：${18423.32}\n"
                    + "收益率：${76.76}%\n"
        )
        println("定投一年：")
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year19)!!.time },
            2019
        )
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year18)!!.time },
            2018
        )
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year17)!!.time },
            2017
        )
        println(
            "三年分别投资\n"
                    + "合计：总投资金额 = ${36000}元，收入总额为：${43943.734}元，"
                    + "总收益：${7943.44},"
                    + "收益率：${22.07}%\n"
        )
    }

    /**
     * 打印每一年的收入详情
     */
    private fun printYear(results: List<FundBean>, year: Int) {
        val numbers: MutableList<Float> = ArrayList()
        //第一种 每个月15号定投1000
        var bean: FundBean? = null
        for (i in 1..13) {
            var currentDate =
                if (i <= 12) "$year-${if (i < 10) "0$i" else i}-14" else "${year + 1}-01-14"
            for (index in 1..10) {
                currentDate = currentDate.substringBeforeLast("-") + "-${14 + index}"
                bean =
                    results.find { format.parse(it.FSRQ!!)!!.time == format.parse(currentDate)!!.time }
                if (bean != null) break
            }
            val result: Float = 1000f.div(bean?.DWJZ?.toFloat()!!)
            if (i <= 12) {
                numbers.add(result)
            } else {
//                println("currentDate = $currentDate, bean  =$bean")
            }
        }
        println("$year 年")
        println(
            "总份额为：${numbers.sum()}\n总投入金额：${1000 * 12}元\n"
                    + "总收益：${numbers.sum() * bean?.DWJZ?.toFloat()!!}\n"
                    + "总盈利：${numbers.sum() * bean.DWJZ?.toFloat()!! - 12000f}\n"
                    + "收益率：${(numbers.sum() * bean.DWJZ?.toFloat()!! - 12000f).div(12000f) * 100}%\n"
        )
    }


    /**
     * 2017/1/1 =1483200000000
     * 2018/1/1 =1514736000000
     * 2019/1/1 =1546272000000
     * 2020/1/1 =1577808000000
     * 第二种策略：短线操作每隔7天卖出并买入（以卖出的金额为买入额），
     * 若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止
     */
    private fun twoCase(fundBeans: List<FundBean>) {
        fun handleCase(time: Long, times: Int, year: String, value: Float): Float {
            val results =
                fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year)!!.time }
            // 以2017年开始为例
            var recordTime = time
            //投资金额
            var amount = value
            //份额
            var share = 0f
            //收益率
            val rate = 11f
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
                    }
                    isInit = false
                    list.add(if (bean.JZZZL.isNullOrBlank()) 0f else bean.JZZZL!!.toFloat())
                    if (recordTime - startTime > sevenDay && list.sum() > rate) {
                        isInit = true
                        amount = share.times(bean.DWJZ!!.toFloat())
                    }
                }
                recordTime += oneDay
            }
            return amount
        }
        //17年
        val result17 = handleCase(time17, 1, year17, 12000f)
        println("2017年投资金额：12000元，收入总额：$result17 元，总收益：${result17-12000f},收益率：${(result17 - 12000f).div(12000f) * 100}%")
        val result18 = handleCase(time18, 1, year18, 12000f)
        println("2018年投资金额：12000元，收入总额：$result18 元，总收益：${result18-12000f},收益率：${(result18 - 12000f).div(12000f) * 100}%")
        val result19 = handleCase(time19, 1, year19, 12000f)
        println("2019年投资金额：12000元，收入总额：$result19 元，总收益：${result19-12000f},收益率：${(result19 - 12000f).div(12000f) * 100}%")
        println(
            "三年分别投资\n"
                    + "合计：总投资金额 = ${36000}元，收入总额为：${result17 + result18 + result19}元，"
                    + "总收益：${result17 + result18 + result19 - 36000f},"
                    + "收益率：${(result17 + result18 + result19 - 36000f).div(12000f) * 100}%"
        )

    }
}

