package com.dht.interest.common.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseDialogFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.interest.R
import com.dht.interest.databinding.FragmentMainDialogBinding

/**
 * @author Administrator
 */
class ExitDialog : BaseDialogFragment() {
    private lateinit var mViewModel: MainDialogViewModel
    private lateinit  var mBinding: FragmentMainDialogBinding
    private var localCallback: LocalCallback<String>? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialog)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        setCenterWindowParams()
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_dialog,
            container,
            false
        )
        return mBinding.root
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel =
            ViewModelProviders.of(this).get(MainDialogViewModel::class.java)
        mBinding.mainDialogViewModel = mViewModel
        bindViews()
    }

    override fun bindViews() {
        super.bindViews()
        mBinding.btnOk.setOnClickListener {
            dismiss()
            localCallback!!.onChangeData()
        }
        mBinding.btnCancel.setOnClickListener { dismiss() }
    }

    /**
     * 设置确定按钮的回调接口
     *
     * @param localCallback 回调接口
     */
    fun setOkCallBack(localCallback: LocalCallback<String>?) {
        this.localCallback = localCallback
    }

    companion object {
        fun newInstance(): ExitDialog {
            return ExitDialog()
        }
    }
}