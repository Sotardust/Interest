package com.dht.interest.common.api.service

import com.dht.interest.investment.BondListModel
import com.dht.interest.investment.fund.FundDataModel
import com.dht.interest.investment.histroy.HistoryModel
import retrofit2.Call
import retrofit2.http.*

/**
 * created by dht on 2020-02-13 19:53
 *
 * @author dht
 */
interface InvestService {


    /**
     * 获取可转债数据列表
     *
     * @param timestamp 时间戳
     * @param is_search Y
     * @param btype C
     * @param listed Y
     * @param rp 50
     */
    @FormUrlEncoded
    @POST("cb_list/")
    fun getConvertibleBondList(
        @Query("___jsl=LST___t") timestamp: Long,
        @Field("is_search") is_search: String,
        @Field("btype") btype: String,
        @Field("listed") listed: String,
        @Field("rp") rp: Int
    ): Call<BondListModel>


    /**
     * 获取可转债数据列表
     *
     * @param bondId id
     * @param timestamp 时间戳
     * @param page 1
     * @param rp 50
     */
    @FormUrlEncoded
    @POST("detail_hist/{bondId}")
    fun getHistoryList(
        @Path("bondId") bondId: String,
        @Query("___jsl=LST___t") timestamp: Long,
        @Field("page") page: Int,
        @Field("rp") rp: Int
    ): Call<HistoryModel>
    //http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183007241219415352096_1583113519435&fundCode=519674&pageIndex=1&pageSize=20&startDate=&endDate=&_=1583118014734
    //http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery183007241219415352096_1583113519435&fundCode=519674&pageIndex=1&pageSize=20&startDate=&endDate=&_=1583113519451
    @GET("f10/lsjz")
    fun getFundHistoryData(
        @Query("callback") callback: String,
        @Query("fundCode") fundCode: String,
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("_") date: Long
    ): Call<FundDataModel>

}