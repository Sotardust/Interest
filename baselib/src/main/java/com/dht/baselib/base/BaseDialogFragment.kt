package com.dht.baselib.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.dht.baselib.R

/**
 * 弹出dialog的基类
 *
 *
 * created by dht on 2018/7/23 10:25
 * @author Administrator
 */
open class BaseDialogFragment : DialogFragment() {
    /**
     * 绑定视图View
     */
   open fun bindViews() {}

    /**
     * 显示菜单、选项、会议室数据列表
     */
    fun show(activity: BaseActivity) {
        val ft =
            activity.supportFragmentManager.beginTransaction()
        show(ft, tag)
    }

    /**
     * 设置菜单窗口属性参数
     */
    fun setBottomWindowParams() {
        val window = dialog!!.window!!
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window.attributes
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        lp.gravity = Gravity.BOTTOM
        lp.dimAmount = 0.5f
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.windowAnimations = R.style.MenuPopAnimation
        window.attributes = lp
    }

    /**
     * 设置选项、会议室窗口属性参数
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun setCenterWindowParams() {
        val window = this.dialog!!.window!!
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window.attributes
        window.setLayout(-1, -2)
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.windowAnimations = R.style.PopupWindowAnimation
        lp.dimAmount = 0.5f
        window.attributes = lp
    }

    companion object {
        private const val TAG = "BaseDialogFragment"
    }
}