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
 *  5、每天投入两百 一周后 ，取出两百
 *
 *  created by Administrator on 2020/3/2 14:19
 */
class FundTest {
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    private val year20 = "2019-12-31"
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
        val money = 1000f
//        val fileName = "assets/yinhechuagnxinchengzhang.json"
        val fileName = "assets/tianhongzhongzhengyinhangzhishuc.json"
        val file = File(fileName)
        val model = Gson().fromJson(file.readText(), FundDataModel::class.java)
        val fundBeans = model.Data?.beans!!
        oneCase(fundBeans, money)
//        println("第二种策略：")
        twoCase(fundBeans, money * 12)
        println("第三种策略：")
//        threeCase(fundBeans)
    }


    /**
     *  第一种策略：
     */
    private fun oneCase(fundBeans: List<FundBean>, money: Float) {

        /**
         * 打印每一年的收入详情
         */
        fun handleYear(results: List<FundBean>, year: Int, times: Int, money: Float): Float {
            val numbers: MutableList<Float> = ArrayList()
            //第一种 每个月15号定投1000
            var bean: FundBean? = null
            for (i in 1..(12 * times + 1)) {

                val mYear = i / 12
                val mMonth = i % 12
                var currentDate =
                    "${(year + mYear)}-${(if (mMonth == 0) 12 else mMonth)}-01"
                if (currentDate == "2020-12-01") currentDate = "2020-01-01"
                for (index in 1..10) {
                    currentDate = currentDate.substringBeforeLast("-") + "-${14 + index}"
                    bean =
                        results.find { format.parse(it.FSRQ!!)!!.time == format.parse(currentDate)!!.time }
                    if (bean != null) break
                }
                val result: Float = money.div(bean?.DWJZ?.toFloat()!!)
                if (i <= 12 * times) {
                    numbers.add(result)
                } else {
//                    println("currentDate = $currentDate, bean  =$bean")
                }
            }
            val value = numbers.sum() * bean?.DWJZ?.toFloat()!!
            val mMoney = money * 12 * times
            println("$year 年 定投：$times 年")
            println(
                "总份额为：${numbers.sum()},总投入金额：${mMoney}元,"
                        + "总收益：${value},"
                        + "总盈利：${value - mMoney},"
                        + "收益率：${(value - mMoney).div(mMoney) * 100}%\n"
            )
            return value
        }

        println("***************************** 第一种策略 *****************************")
        println("********                                                    *********")
        println("********                 每个月15号定投1000元                  ********")
        println("********                                                    *********")
        println("*********************************************************************")

        val result17 = handleYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year17)!!.time },
            2017, 1, money
        )
        val result18 = handleYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year18)!!.time },
            2018, 1, money
        )

        val result19 = handleYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year19)!!.time },
            2019, 1, money
        )

        val result3 = handleYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year17)!!.time },
            2017, 3, money
        )
        val result2 = handleYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year18)!!.time },
            2018, 2, money
        )

        println(
            "三年分别投资\n"
                    + "合计：总投资金额 = ${money * 12 * 3}元，收入总额为：${result17 + result18 + result19}元，"
                    + "总收益：${result17 + result18 + result19 - money * 12 * 3},"
                    + "收益率：${(result17 + result18 + result19 - money * 12 * 3).div(money * 12 * 3) * 100}%\n"
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
    private fun twoCase(fundBeans: List<FundBean>, money: Float) {
        /**
         * @param time 开始时间 eg 2017/1/1 =1483200000000
         * @param times 投资年限 1、2、3年
         * @param year 使用年限过滤数据 "2016-12-31"
         * @param value 初始投资金额 12000元
         */
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
            val rate =5f
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

        println("***************************** 第二种策略 *****************************")
        println("***                                                           ******")
        println("***       短线操作每隔7天卖出并买入（以卖出的金额为买入额）         ******")
        println("***   若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止    ******")
        println("***                                                           ******")
        println("********************************************************************")
        //17年
        val result17 = handleCase(time17, 1, year17, money)
        println(
            "2017年投资金额：$money 元，收入总额：$result17 元，总收益：${result17 - money},收益率：${(result17 - money).div(
                money
            ) * 100}%"
        )
        val result18 = handleCase(time18, 1, year18, money)
        println(
            "2018年投资金额：$money 元，收入总额：$result18 元，总收益：${result18 - money},收益率：${(result18 - money).div(
                money
            ) * 100}%"
        )
        val result19 = handleCase(time19, 1, year19, money)
        println(
            "2019年投资金额：$money 元，收入总额：$result19 元，总收益：${result19 - money},收益率：${(result19 - money).div(
                money
            ) * 100}%"
        )
        println(
            "三年分别投资\n"
                    + "合计：总投资金额 = ${money * 3}元，收入总额为：${result17 + result18 + result19}元，"
                    + "总收益：${result17 + result18 + result19 - money * 3},"
                    + "收益率：${(result17 + result18 + result19 - money * 3).div(money * 3) * 100}%\n"
        )
        val result3 = handleCase(time17, 3, year17, money * 3)
        println(
            "2017年开始投资三年投资金额：${money * 3} 元，收入总额：$result3 元，总收益：${result3 - money * 3},收益率：${(result3 - money * 3).div(
                money * 3
            ) * 100}%"
        )
        val result2 = handleCase(time18, 2, year18, money * 2)
        println(
            "2018年开始投资两年投资金额：${money * 2} 元，收入总额：$result2 元，总收益：${result2 - money * 2},收益率：${(result2 - money * 2).div(
                money * 2
            ) * 100}%"
        )

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

        handleCase(time18, 1, year18, 6f)
    }
}

