package com.dht.interest.util

import androidx.room.TypeConverter


/**
 * created by Administrator on 2018/11/19 15:36
 */
enum class CallType {
    //接听
    ANSWER,  //拨打
    DIAL,  //未接
    MISSED,  //拒接
    REJECT,  //其他
    OTHER;

    companion object {
        @TypeConverter
        fun getCallType(index: Int): CallType {
            return when (index) {
                1 -> ANSWER
                2 -> DIAL
                3 -> MISSED
                5 -> REJECT
                else -> OTHER
            }
        }
    }
}