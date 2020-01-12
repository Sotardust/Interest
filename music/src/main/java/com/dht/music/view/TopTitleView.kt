package com.dht.music.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.dht.baselib.base.BaseActivity
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.LocalCallback
import com.dht.baselib.util.SimpleTextWatcher
import com.dht.baselib.util.toastCustomTime
import com.dht.database.bean.music.MusicBean
import com.dht.eventbus.RxBus
import com.dht.eventbus.RxCallBack
import com.dht.eventbus.event.UpdateTopPlayEvent
import com.dht.music.R

/**
 * created by dht on 2019/1/15 11:10
 *
 * @author Administrator
 */
class TopTitleView : LinearLayout, View.OnClickListener {
    private lateinit var back: ImageView
    private lateinit var title: TextView
    private lateinit var searchEdit: EditText
    private lateinit var search: TextView
    private lateinit var setting: ImageView
    private lateinit var playRelative: RelativeLayout
    private lateinit var songName: TextView
    private lateinit var author: TextView
    private lateinit var shared: ImageView
    private var mContext: Context
    private var activity: BaseActivity? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        orientation = VERTICAL
        initView()
    }

    /**
     * 初始化视图View
     */
    private fun initView() {
        val view =
            View.inflate(mContext, R.layout.view_title_top, this)
        back = view.findViewById(R.id.top_title_back)
        title = view.findViewById(R.id.top_title)
        //搜索框
        searchEdit = view.findViewById(R.id.top_edit)
        searchEdit.visibility = View.GONE
        //搜索按钮
        search = view.findViewById(R.id.top_search)
        //其他按钮
        setting = view.findViewById(R.id.top_setting)
        //播放音乐titleBar显示
        playRelative = view.findViewById(R.id.top_song_info)
        songName = view.findViewById(R.id.top_song_name)
        author = view.findViewById(R.id.top_author)
        //分享按钮
        shared = view.findViewById(R.id.top_shared)
        back.setOnClickListener(this)
        search.setOnClickListener(this)
        setting.setOnClickListener(this)
    }

    /**
     * 设置与之关联的activity
     *
     * @param activity Activity
     */
    fun setActivity(activity: BaseActivity) {
        this.activity = activity
    }

    /**
     * 设置文本框监听回调事件
     *
     * @param textWatcher SimpleTextWatcher
     */
    fun setSearchEditTextWatcher(textWatcher: SimpleTextWatcher?) {
        searchEdit.addTextChangedListener(textWatcher)
    }

    /**
     * 设置分享按钮的回调接口
     *
     * @param localCallback 本地回调接口
     */
    fun setSharedCallback(localCallback: LocalCallback<MusicBean?>) {
        shared.setOnClickListener { localCallback.onChangeData(BaseFragment.service.currentMusic) }
    }

    /**
     * 更新视图view 设置title值
     *
     * @param activity Activity
     * @param value    值
     */
    fun updateView(activity: BaseActivity, value: String?) {
        this.activity = activity
        activity.runOnUiThread {
            title.visibility = View.VISIBLE
            title.text = value
        }
    }

    /**
     * 更新视图View
     */
    fun updatePlayView() {
        RxBus.INSTANCE.toRxBusResult<UpdateTopPlayEvent>(object : RxCallBack {
            override fun onCallBack(data: Any) {
                val music = BaseFragment.service.currentMusic
                playRelative.visibility = View.VISIBLE
                songName.text = music.name
                author.text = music.author
            }

        })
    }

    /**
     * 显示本地音乐页title
     */
    fun setLocalTitleBar() {
        title.visibility = View.VISIBLE
        search.visibility = View.VISIBLE
        setting.visibility = View.VISIBLE
    }

    /**
     * 显示最近播放页title
     */
    fun showRecentPlayTitleBar() {
        title.visibility = View.VISIBLE
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.top_title_back -> activity?.finish()
            R.id.top_search -> {
                val isSearch =
                    context.getString(R.string.search) == search.text.toString()
                title.visibility = if (isSearch) View.GONE else View.VISIBLE
                if (!isSearch && !TextUtils.isEmpty(searchEdit.text)) {
                    searchEdit.text = null
                }
                searchEdit.visibility = if (isSearch) View.VISIBLE else View.GONE
                search.setText(if (isSearch) R.string.close_search else R.string.search)
            }
            R.id.top_setting -> mContext.toastCustomTime("功能待开发")
        }
    }

    companion object {
        private const val TAG = "TopTitleView"
    }
}