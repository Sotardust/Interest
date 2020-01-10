package com.dht.interest.phone

import android.annotation.SuppressLint
import android.app.Application
import android.database.Cursor
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.repository.AllCallsRepository
import com.dht.interest.repository.entity.AllCallsEntity
import com.dht.interest.util.AllCallsType
import com.dht.interest.util.CallType
import java.text.SimpleDateFormat
import java.util.*

/**
 * created by Administrator on 2018/10/26 09:52
 */
open class CallRecordViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {
    private val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    private val allCallList: ArrayList<AllCallsEntity> = ArrayList()
    protected var repository: AllCallsRepository = AllCallsRepository(application)
    /**
     * 查找全部通话记录
     */
    @SuppressLint("MissingPermission", "HardwareIds", "NewApi")
    private fun findLocalAllCalls() { //        ContentResolver cr = application.getApplicationContext().getContentResolver();
//        Uri uri = CallLog.Calls.CONTENT_URI;
//        TelephonyManager tm = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
//        String number1;
//        try {
//            if (tm != null) {
//                number1 = tm.getLine1Number();
//                Log.d(TAG, " number1 = " + number1 +
//                        " 手机号个数：" + tm.getPhoneCount() +
//                        " getGroupIdLevel1：" + tm.getGroupIdLevel1() +
//                        " getSimSerialNumber：" + tm.getSimSerialNumber() +
//                        " getSubscriberId：" + tm.getSubscriberId() +
//                        " getDeviceSoftwareVersion：" + tm.getDeviceSoftwareVersion() +
//                        " getImei(1)：" + tm.getImei(1) +
//                        " getImei(2)：" + tm.getImei(2) +
//                        " getMeid(1)：" + tm.getMeid(1) +
//                        " getMeid(2)：" + tm.getMeid(2) +
//                        " getSimState(1)：" + tm.getSimState(1) +
//                        " getSimState(2)：" + tm.getSimState(2) +
//                        " getDeviceId(1)：" + tm.getDeviceId(1) +
//                        " getDeviceId(2)：" + tm.getDeviceId(2)
//                );
//            }
//            @SuppressLint("InlinedApi")
//            String[] projection = new String[]{CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE,
//                    CallLog.Calls.TYPE, CallLog.Calls.NUMBER_PRESENTATION, CallLog.Calls.DURATION, CallLog.Calls.IS_READ};
//
//            Cursor cursor = cr.query(uri, projection, null, null, null);
//            if (cursor == null) return;
//            while (cursor.moveToNext()) {
//                allCallList.add(getAllCalls(cursor));
//            }
//            cursor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "findLocalAllCalls: e", e);
//        }
    }

    /**
     * 填充实体类
     *
     * @param cursor Cursor
     * @return AllCalls实体数据
     */
    private fun getAllCalls(cursor: Cursor): AllCallsEntity {
        val allCallsEntity = AllCallsEntity()
        allCallsEntity.name = (if (cursor.getString(0) == null) "未知" else cursor.getString(0))
        allCallsEntity.callNumber = (cursor.getString(1))
        allCallsEntity.callTime = (format.format(cursor.getLong(2)))
        allCallsEntity.callType = (CallType.getCallType(cursor.getInt(3)))
        allCallsEntity.singleTime = (cursor.getInt(5))
        return allCallsEntity
    }

    /**
     * type 1/2/3/4/5 接听/拨打/未接//拒接
     * 设置其他AllCalls数据
     */
    private fun setAllCallsOtherData() {
        for (allCallsEntity in allCallList) {
            when (allCallsEntity.callType) {
                CallType.ANSWER -> {
                    allCallsEntity.type = (AllCallsType.ANSWER)
                    ++allCallsEntity.receiverTimes
                }
                CallType.DIAL -> {
                    allCallsEntity.type = (AllCallsType.DIAL)
                    ++allCallsEntity.dialTimes
                }
                CallType.MISSED -> {
                    ++allCallsEntity.missedTimes
                    allCallsEntity.type = (AllCallsType.MISSED)
                }
                CallType.REJECT -> {
                    allCallsEntity.type = (AllCallsType.REJECT)
                    ++allCallsEntity.refuseTimes
                }
            }
            allCallsEntity.totalTime = (allCallsEntity.totalTime + allCallsEntity.singleTime)
        }
    }

    /**
     * 去重 自定义0 为获取全部通话记录
     * 1/2/3/4/5 接听/拨打/未接//拒接
     *
     * @param localCallback AllCalls 实体集合
     */
    protected fun distinctAllCalls(localCallback: LocalCallback<List<AllCallsEntity>>) {
        if (IS_FIRST) {
            val numberList =
                ArrayList<String>()
            val allCallsHM: HashMap<String, AllCallsEntity> = HashMap()
            for (allCallsEntity in allCallList) {
                if (numberList.contains(allCallsEntity.callNumber)) {
                    val calls = allCallsHM[allCallsEntity.callNumber]
                    calls!!.totalTime=(calls.singleTime + allCallsEntity.singleTime)
                    calls.refuseTimes =(calls.refuseTimes + allCallsEntity.refuseTimes)
                    calls.missedTimes =(calls.missedTimes + allCallsEntity.missedTimes)
                    calls.receiverTimes =(calls.receiverTimes + allCallsEntity.receiverTimes)
                    calls.dialTimes =(calls.dialTimes + allCallsEntity.dialTimes)
                } else {
                    numberList.add(allCallsEntity.callNumber)
                    allCallsHM[allCallsEntity.callNumber] = allCallsEntity
                }
            }
            for (s in allCallsHM.keys) {
                repository.addAllCallsEntity(allCallsHM[s]!!)
            }
            IS_FIRST = !IS_FIRST
        }
        repository.getAllCallsEntities(localCallback)
    }

    companion object {
        private const val TAG = "CallRecordViewModel"
        private var IS_FIRST = true
    }

    init {
        if (IS_FIRST) {
            findLocalAllCalls()
            setAllCallsOtherData()
        }
    }
}