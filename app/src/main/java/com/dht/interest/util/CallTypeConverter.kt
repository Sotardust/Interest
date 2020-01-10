package com.dht.interest.util

import androidx.room.TypeConverter
import com.dht.interest.util.CallType

/**
 * created by Administrator on 2018/11/19 16:41
 */
object CallTypeConverter {
    @TypeConverter
    fun toCallType(index: Int): CallType {
        return when (index) {
            1 -> CallType.ANSWER
            2 -> CallType.DIAL
            3 -> CallType.MISSED
            5 -> CallType.REJECT
            else -> CallType.OTHER
        }
    }

    @TypeConverter
    fun toInteger(type: CallType?): Int {
        return when (type) {
            CallType.ANSWER -> 1
            CallType.DIAL -> 2
            CallType.MISSED -> 3
            CallType.REJECT -> 5
            else -> 0
        }
    }
}