package com.dht.interest.investment.histroy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dht.baselib.base.BaseActivity
import com.dht.interest.R

/**
 * created by dht on 2020-02-14 16:27
 *
 * @author dht
 */
class HistoryActivity : BaseActivity() {

    companion object {
        const val ARG_BOND_ID = "bond_id"
        const val ARG_BOND_NAME = "bond_name"
        fun start(context: Context, bondId: String, bondName: String) {
            val intent = Intent(context, HistoryActivity::class.java)
            intent.putExtras(Bundle().apply {
                putString(ARG_BOND_ID, bondId)
                putString(ARG_BOND_NAME, bondName)
            })
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HistoryFragment.newInstance(intent.extras!!))
                .commitNow()
        }
    }

}