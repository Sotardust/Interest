package com.dht.interest.common.api.service

import com.dht.interest.investment.BondListModel
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


}