package com.dht.interest

import com.dht.interest.investment.fund.FundTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun fundTest() {
        val result = FundTest()
        result.execute()
    }
    @Test
    fun testData(){
        val totalStuff = 2000
        val price = 1.3f
    }
}
