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
//        val fileName = "assets/tianhongzhongzhengyinhangzhishuA.json"
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
     *  第一种策略：每个月15号定投1000
     */
    private fun oneCase(fundBeans: List<FundBean>, money: Float) {
        /**
         * 打印每一年的收入详情
         */
        fun handleYear(results: List<FundBean>, years: Int, period: Int, money: Float) {
            val numbers: MutableList<Float> = ArrayList()
            var bean: FundBean? = null
            for (i in 1..(12 * period + 1)) {
                val mYear = i / 12
                val mMonth = i % 12
                var currentDate =
                    "${(years + mYear)}-${(if (mMonth == 0) 12 else mMonth)}-01"
                if (currentDate == "2020-12-01") currentDate = "2020-01-01"
                for (index in 1..15) {
                    currentDate = currentDate.substringBeforeLast("-") + "-${14 + index}"
                    bean =
                        results.find { format.parse(it.FSRQ!!)!!.time == format.parse(currentDate)!!.time }
                    if (bean != null) break
                }
                if (bean == null) return
                val result: Float = money.div(bean.DWJZ?.toFloat()!!)
                if (i <= 12 * period) {
                    numbers.add(result)
                } else {
//                    println("currentDate = $currentDate, bean  =$bean")
                }
            }


            val value = numbers.sum() * bean?.DWJZ?.toFloat()!!
            val mMoney = money * 12 * period
            print("$years-01开始 定投：$period 年,")
            println(
                "总份额为：${numbers.sum()},本金：${mMoney}元,"
                        + "总金额：$value,"
                        + "持有收益：${value - mMoney},"
                        + "收益率：${(value - mMoney).div(mMoney) * 100}%"
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
        for (i in 1..20) {
            val year = initYears - i
            val date = "$year-01-01"
            handleYear(
                fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(date)!!.time },
                year, 1, money
            )
        }
        println("--------------------------------------------------------------------")
        //定投年限
        for (i in 1..20) {
            val year = initYears - i
            val date = "$year-01-01"
            handleYear(
                fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(date)!!.time },
                year, i, money
            )
        }

    }


    /**
     *
     * 第二种策略：短线操作每隔7天卖出并买入（以卖出的金额为买入额），
     * 若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止
     */
    private fun twoCase(fundBeans: List<FundBean>, money: Float) {
        /**
         * @param years  开始时间 eg 2017/1/1
         * @param period 投资年限 1、2、3年
         * @param value 初始投资金额 12000元
         */
        fun handleCase(years: Int, period: Int, value: Float) {
            // 以2017年开始为例
            var recordCurrentTime = format.parse("$years-01-01")!!.time
            val results =
                fundBeans.filter { format.parse(it.FSRQ!!)!!.time >= recordCurrentTime }
            //投资金额
            var amount = value
            //份额
            var share = 0f
            //收益率
            val rate = 5f
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
            for (i in 1..(365 * period)) {
                val bean = results.find { it.FSRQ == format.format(recordCurrentTime) }
                if (bean != null) {
                    if (isInit) {
                        share = amount.div(bean.DWJZ!!.toFloat())
                        startTime = recordCurrentTime
                    }
                    isInit = false
                    list.add(if (bean.JZZZL.isNullOrBlank()) 0f else bean.JZZZL!!.toFloat())
                    println("share = $share ,amount =$amount，时间：${(recordCurrentTime - startTime).div(oneDay)}天，收益率 =${list.sum()} ,bean = ${bean}")
                    if (recordCurrentTime - startTime > sevenDay && list.sum() > rate) {
                        isInit = true
                        amount = share.times(bean.DWJZ!!.toFloat())
                    }
                    isInvest = true
                }
                if (i > 90 && !isInvest) {
                    return
                }
                recordCurrentTime += oneDay
            }
            if (!isInvest) return

            print("$years-01开始 投资：$period 年")
            println(
                ",本金：${value}元,"
                        + "总金额：$amount,"
                        + "持有收益：${amount - value},"
                        + "收益率：${(amount - value).div(value) * 100}%"
            )
        }

        println("***************************** 第二种策略 *****************************")
        println("***                                                           ******")
        println("***       短线操作每隔7天卖出并买入（以卖出的金额为买入额）         ******")
        println("***   若7天内收益相加 <= 5% 则不卖出，一直持有到收益率 >= 5%为止    ******")
        println("***                                                           ******")
        println("********************************************************************")

        //初始年限
        val initYears = 2020
        //投资一年时间收益
        for (i in 1..20) {
            val years = initYears - i
            handleCase(years, 1, money)
        }

        println("--------------------------------------------------------------------")

        //投资n年时间收益
        for (i in 1..20) {
            val years = initYears - i
            handleCase(years, i, money * i)
        }
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

