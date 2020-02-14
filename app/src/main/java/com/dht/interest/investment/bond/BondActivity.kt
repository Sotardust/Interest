package com.dht.interest.investment.bond

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dht.baselib.base.BaseActivity
import com.dht.interest.R
import com.dht.interest.investment.BondBean

/**
 * created by dht on 2020-02-14 15:03
 *
 * @author dht
 */
class BondActivity : BaseActivity() {

    companion object {
        const val ARG_BEAN = "bean"
        fun start(context: Context, bean: BondBean) {
            val intent = Intent(context, BondActivity::class.java)
            intent.putExtras(Bundle().apply { putParcelable(ARG_BEAN, bean) })
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BondFragment.newInstance(intent.extras!!))
                .commitNow()
        }
    }

}