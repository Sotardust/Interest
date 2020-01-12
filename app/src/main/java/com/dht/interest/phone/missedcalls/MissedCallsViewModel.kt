package com.dht.interest.phone.missedcalls

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.dht.baselib.base.BaseAndroidViewModel
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.repository.AllCallsRepository
import com.dht.interest.repository.entity.AllCallsEntity
import java.util.*

/**
 * @author Administrator
 */
class MissedCallsViewModel(@NonNull application: Application) :
    BaseAndroidViewModel(application) {
    //    private IWXAPI iwxapi;
    private val repository: AllCallsRepository = AllCallsRepository(application)
    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private var mMissedCallsList: MutableLiveData<ArrayList<AllCallsEntity>>? = null

    val missedCallsList: MutableLiveData<ArrayList<AllCallsEntity>>
        get() {
            if (mMissedCallsList == null) {
                mMissedCallsList = MutableLiveData()
                distinctMissedCalls()
            }
            return mMissedCallsList!!
        }
    //
//    private IWXAPI getIwxapi(Context context) {
//        //微信AppID
//        final String APP_ID = "wx4b46660f04a64bc4";
//        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
//        iwxapi.registerApp(APP_ID);
//        return iwxapi;
//    }
    /**
     * 获取未接来电通话记录
     * 未接类型：3
     *
     * @return AllCalls实体集合
     */
    private fun distinctMissedCalls() {
        repository.getCallsEntities(object :
            LocalCallback<List<AllCallsEntity>>() {
            override fun onChangeData(data: List<AllCallsEntity>?) {
                mMissedCallsList?.setValue(data as ArrayList<AllCallsEntity>)
            }
        }, "3")
    }

    /**
     * 测试向微信发送消息
     *
     * @param text
     */
    fun sendMessageToWeChat(text: String?) {
        //        WXTextObject textObject = new WXTextObject();
//        textObject.text = "测试数据分享到微信";
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObject;
//        msg.description = "***测试数据分享到微信***";
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
////        req.transaction = String.valueOf(format.format(System.currentTimeMillis()));
//        req.transaction = "text";
//        req.message = msg;
////        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//        iwxapi.sendReq(req);
    }

    companion object {
        private const val TAG = "MissedCallsViewModel"
    }

    init {
        //        iwxapi = getIwxapi(application.getApplicationContext());
    }
}