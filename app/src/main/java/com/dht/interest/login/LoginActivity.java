package com.dht.interest.login;

import android.os.Bundle;

import com.dht.interest.R;
import com.dht.baselib.base.BaseActivity;

/**
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }

}
