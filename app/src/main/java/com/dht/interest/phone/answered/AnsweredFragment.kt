package com.dht.interest.phone.answered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.interest.R
import com.dht.interest.common.adapter.CallRecordAdapter
import com.dht.interest.databinding.FragmentAnsweredBinding
import com.dht.interest.repository.entity.AllCallsEntity
import kotlinx.android.synthetic.main.fragment_answered.*


class AnsweredFragment : BaseFragment() {
    private lateinit var mViewModel: AnsweredViewModel
    private lateinit var mBinding: FragmentAnsweredBinding
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_answered, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(AnsweredViewModel::class.java)
        mBinding.answeredViewModel = mViewModel
        bindViews()
        // TODO: Use the ViewModel
    }

    override fun bindViews() {
        super.bindViews()
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val callRecordAdapter = CallRecordAdapter(recycleItemClickCallBack)
        mViewModel.answeredCallsList
            .observe(this, androidx.lifecycle.Observer {
                callRecordAdapter.setChangeList(it!!)
            })
        recyclerView.adapter = callRecordAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(VerticalDecoration(2))
    }

    private val recycleItemClickCallBack: RecycleItemClickCallBack<AllCallsEntity> =
        object : RecycleItemClickCallBack<AllCallsEntity>() {
            override fun onItemClickListener(
                value: AllCallsEntity?,
                position: Int?
            ) {
                super.onItemClickListener(value, position)
            }
        }

    companion object {
        fun newInstance(): AnsweredFragment {
            return AnsweredFragment()
        }
    }
}