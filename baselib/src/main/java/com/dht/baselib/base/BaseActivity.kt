package com.dht.baselib.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dht.baselib.R

/**
 * created by dht on 2018/7/3 16:45
 *
 * @author Administrator
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    /**
     * 获取布局文件 Id
     *
     * @return Int
     */
//    protected abstract int getLayoutId ();
    /**
     * 向Activity中添加 添加Fragment
     */
//    protected abstract void addFragmentCommitNow ();
}