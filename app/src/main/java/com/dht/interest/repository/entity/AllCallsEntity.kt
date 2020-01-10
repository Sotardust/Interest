package com.dht.interest.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dht.interest.util.CallType
import com.dht.interest.util.CallTypeConverter

/**
 * 记录所有通话记录
 *
 *
 * created by Administrator on 2018/10/24 18:32
 */
@Entity(tableName = "allCalls")
class AllCallsEntity {
    /**
     * 姓名
     */
    @ColumnInfo(name = "name")
    lateinit var name: String
    /**
     * 拨号方手机号
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    lateinit var callNumber: String
    /**
     * 接收方手机号
     */
    @ColumnInfo(name = "receiver_number")
    lateinit var receiverNumber: String
    /**
     * 地址
     */
    @ColumnInfo(name = "address")
    var address: String? = null
    /**
     * 拨打时间
     */
    @ColumnInfo(name = "call_time")
    var callTime: String? = null
    /**
     * 通话总时长
     */
    @ColumnInfo(name = "total_time")
    var totalTime = 0
    /**
     * 单次通话时长
     */
    @ColumnInfo(name = "single_name")
    var singleTime = 0
    /**
     * 类型
     */
    @ColumnInfo(name = "type")
    var type: String? = null
    /**
     * 拨打类型
     */
    @ColumnInfo(name = "call_type")
    @TypeConverters(CallTypeConverter::class)
    var callType: CallType? = null
    /**
     * 拨打次数
     */
    @ColumnInfo(name = "dial_times")
    var dialTimes = 0
    /**
     * 接通次数
     */
    @ColumnInfo(name = "receiver_times")
    var receiverTimes = 0
    /**
     * 未接次数
     */
    @ColumnInfo(name = "missed_times")
    var missedTimes = 0
    /**
     * 拒接次数
     */
    @ColumnInfo(name = "refuse_times")
    var refuseTimes = 0
    /**
     * 是否发送成功
     */
    @ColumnInfo(name = "is_send_successful", index = false)
    var isSendSuccessful = false

    val times: Int
        get() = dialTimes + receiverTimes + missedTimes + refuseTimes

}