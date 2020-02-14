package com.dht.interest.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.interest.R
import com.dht.interest.ViewModelFactory
import com.dht.interest.common.adapter.LinkageAdapter
import com.dht.interest.common.adapter.NextLinkageAdapter
import com.dht.interest.databinding.FragmentSecondaryLinkageBinding
import kotlinx.android.synthetic.main.fragment_secondary_linkage.*
import java.util.*

class SecondaryLinkageFragment : BaseFragment() {

    private lateinit var mViewModel: SecondaryLinkageViewModel
    private lateinit var mBinding: FragmentSecondaryLinkageBinding
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_secondary_linkage, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mViewModelFactory = ViewModelFactory.getInstance()
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(SecondaryLinkageViewModel::class.java)
        mBinding.secondaryLinkageViewModel = mViewModel
        bindViews()
        // TODO: Use the ViewModel
    }

    private var adapters: ArrayList<NextLinkageAdapter> = ArrayList()

    override fun bindViews() {
        super.bindViews()
        //        mBinding.flowLayout.set
        val list = ArrayList<String>()
        val nextlist1 = ArrayList<String>()
        val nextlist2 = ArrayList<String>()
        val nextlist3 = ArrayList<String>()
        val nextlist4 = ArrayList<String>()
        val nextlist5 = ArrayList<String>()
        val lists: MutableList<List<String>> =
            ArrayList()
        for (i in 0..4) {
            list.add("测试 $i")
        }
        for (i in 0..14) {
            nextlist2.add("测试1下 $i")
            nextlist3.add("测试2下 $i")
            nextlist4.add("测试3下 $i")
            nextlist5.add("测试4下 $i")
        }
        nextlist1.add("测试一二三四五六")
        nextlist1.add("测试yi")
        nextlist1.add("测试ersan")
        nextlist1.add("测试一")
        nextlist1.add("测试一二三四五六")
        nextlist1.add("测二三四五六1")
        nextlist1.add("测试一二测试一二三四五六2")
        nextlist1.add("测试测试测")
        nextlist1.add("说什么好呢")
        nextlist1.add("遇见")
        nextlist1.add("光年之外")
        nextlist1.add("光年之外")
        nextlist1.add("我有我爱我")
        nextlist1.add("红")
        nextlist1.add("可能否")
        nextlist1.add("空空如也")
        nextlist1.add("狠人大帝")
        for (value in nextlist1) {
            val textView = LayoutInflater.from(context).inflate(
                R.layout.flowlayout_textview,
                mBinding.flowLayout,
                false
            ) as TextView
            textView.text = value
            textView.setOnClickListener(object : View.OnClickListener {
                var isClick = false
                override fun onClick(view: View) {
                    Log.d(
                        "dht",
                        "onClick: index = " + nextlist1.indexOf(value) + ", value = " + value
                    )
                    textView.setBackgroundResource(if (isClick) R.drawable.bound_recycle_item else R.drawable.bound_recycle_item_blue)
                    isClick = !isClick
                }
            })
            mBinding.flowLayout.addView(textView)
        }
        lists.add(nextlist1)
        lists.add(nextlist2)
        lists.add(nextlist3)
        lists.add(nextlist4)
        lists.add(nextlist5)
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linkageAdapter = LinkageAdapter(recycleItemClickCallBack)
        val adapter1 = NextLinkageAdapter(nextRecycleItemClickCallBack)
        adapter1.setChangeList(nextlist1)
        val adapter2 = NextLinkageAdapter(nextRecycleItemClickCallBack)
        adapter2.setChangeList(nextlist2)
        val adapter3 = NextLinkageAdapter(nextRecycleItemClickCallBack)
        adapter3.setChangeList(nextlist3)
        val adapter4 = NextLinkageAdapter(nextRecycleItemClickCallBack)
        adapter4.setChangeList(nextlist4)
        val adapter5 = NextLinkageAdapter(nextRecycleItemClickCallBack)
        adapter5.setChangeList(nextlist5)
        adapters.add(adapter1)
        adapters.add(adapter2)
        adapters.add(adapter3)
        adapters.add(adapter4)
        adapters.add(adapter5)
        linkageAdapter?.setChangeList(context, list, lists)
        recyclerView.adapter = linkageAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(VerticalDecoration(2))
    }

    private var linkageAdapter: LinkageAdapter? = null
    private val recycleItemClickCallBack: RecycleItemClickCallBack<String> =
        object : RecycleItemClickCallBack<String>() {
            var isClick = true
            override fun onItemClickListener(
                value: String?,
                position: Int?
            ) {
                super.onItemClickListener(value, position)
                isClick = !isClick
                linkageAdapter?.setShowLines(1)
                //            adapters.get(position).setShowAll(isClick);
            }
        }
    private val nextRecycleItemClickCallBack: RecycleItemClickCallBack<String> =
        object : RecycleItemClickCallBack<String>() {
            override fun onItemClickListener(
                value: String?,
                position: Int?
            ) {
                super.onItemClickListener(value, position)
                Log.d(
                    "dht",
                    "next onItemClickListener string: $value, position = $position"
                )
            }
        }

    companion object {
        fun newInstance(): SecondaryLinkageFragment {
            return SecondaryLinkageFragment()
        }
    }
}