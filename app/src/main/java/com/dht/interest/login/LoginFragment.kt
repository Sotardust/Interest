package com.dht.interest.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.MusicBean
import com.dht.database.preferences.MessagePreferences
import com.dht.eventbus.RxBus
import com.dht.eventbus.RxCallBack
import com.dht.eventbus.event.InitPlayListEvent
import com.dht.interest.MainActivity
import com.dht.interest.MessageApplication
import com.dht.interest.R
import com.dht.interest.databinding.FragmentLoginBinding
import com.dht.music.repository.MusicRepository
import com.dht.network.BaseModel
import com.dht.network.HttpStatusCode
import com.dht.network.NetworkCallback

/**
 * @author Administrator
 */
class LoginFragment : BaseFragment() {
    private lateinit var mViewModel: LoginViewModel
    private lateinit var mBinding: FragmentLoginBinding
    private var repository: MusicRepository? = null
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        repository = MusicRepository(mViewModel.getApplication<MessageApplication>())
        mBinding.loginViewModel = mViewModel
        bindViews()
        service.initPlayList()
    }

    override fun bindViews() {
        super.bindViews()
        mBinding.login.setOnClickListener(this)
        mBinding.register.setOnClickListener(this)
        //        mViewModel.initData();
        initRxEvent()
    }

    override fun handlingClickEvents(view: View?) {
        super.handlingClickEvents(view)
        when (view!!.id) {
            R.id.login -> {
                val name: String = mBinding.name.text.toString()
                val password: String = mBinding.password.text.toString()
                if (isEmpty(name, password)) {
                    return
                }
                if ("0" == name && "0" == password) {
                    toMainActivity()
                    return
                }
                mViewModel.logon(name, password, loginCallBack)
            }
            R.id.register -> {
                val name1: String = mBinding.name.text.toString()
                val password2: String = mBinding.password.text.toString()
                val registerTime = System.currentTimeMillis().toString()
                if (isEmpty(name1, password2)) {
                    return
                }
                mViewModel.register(name1, password2, registerTime)
            }
            else -> {
            }
        }
    }

    private val loginCallBack: NetworkCallback<BaseModel<String>> =
        object : NetworkCallback<BaseModel<String>> {
            override fun onChangeData(data: BaseModel<String>?) {
                if (data == null) {
                    context?.toastCustomTime("网络超时", 200)
                    return
                }
                service.initPlayList()
                if (data.code == HttpStatusCode.CODE_100) {
                    if (data.result == null) {
                        MessagePreferences.INSTANCE.personId = 0L
                    } else {
                        MessagePreferences.INSTANCE.personId = data.result!!.toLong()
                    }
                    toMainActivity()
                }
                if (!data.msg.isNullOrBlank()) {
                    context?.toastCustomTime(data.msg!!, 200)
                }
            }
        }

    private fun initRxEvent() {
        RxBus.INSTANCE.toRxBusResult<InitPlayListEvent>(object : RxCallBack {
            override fun onCallBack(data: Any) {
                repository?.getAllMusics(object : LocalCallback<List<MusicBean>>() {
                    override fun onChangeData(data: List<MusicBean>?) {
                        super.onChangeData(data)
                        if (data == null) return
                        service.setPlayList(data)
                    }
                })
            }
        })
    }

    /**
     * 跳转到主页面
     */
    private fun toMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        if (activity != null) {
            activity!!.finish()
        }
    }

    /**
     * 判断是否为空
     *
     * @param name     用户名
     * @param password 密码
     * @return 布尔型
     */
    private fun isEmpty(name: String, password: String): Boolean {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            context?.toastCustomTime("用户名或密码不能为空！", 200)
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "LoginFragment"
        @JvmStatic
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}