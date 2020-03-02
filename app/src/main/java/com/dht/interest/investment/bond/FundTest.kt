package com.dht.interest.investment.bond

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
    /**
     *读取银河创新成长
     */
    fun readYinHe() {
        val year1 = "2018-12-31"
        val year2 = "2017-12-31"
        val year3 = "2016-12-31"
        val fileName = "assets/yinhechuagnxinchengzhang.json"
        val file = File(fileName)
        val model = Gson().fromJson<FundDataModel>(file.readText(), FundDataModel::class.java)
        val fundBeans = model.Data.beans
        println("第一种策略：")
        println("定投三年：")
        println("定投二年：")
        println("定投一年：")
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year1)!!.time },
            2019
        )
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year2)!!.time },
            2018
        )
        printYear(
            fundBeans.filter { format.parse(it.FSRQ!!)!!.time > format.parse(year3)!!.time },
            2017
        )

        println("第二种策略：")
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
                println("currentDate = $currentDate, bean  =$bean")
            }
        }
        println("$year 年")
        println(
            "总份额为：${numbers.sum()}\n总投入金额：${1000 * 12}元\n"
                    + "总收益：${numbers.sum() * bean?.DWJZ?.toFloat()!!}\n"
                    + "总盈利：${numbers.sum() * bean.DWJZ?.toFloat()!! - 12000f}\n"
                    + "年收益率：${(numbers.sum() * bean.DWJZ?.toFloat()!! - 12000f).div(12000f) * 100}%\n"
        )
    }
}

