package com.dht.interest.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * created by Administrator on 2020/3/30 09:45
 */
@SuppressLint("AppCompatCustomView")
public class FilterEditText extends EditText {
    public FilterEditText(Context context) {
        super(context);
        initFilter();
    }

    public FilterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFilter();
    }

    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFilter();
    }

    private void initFilter() {
        this.getText().setFilters(new InputFilter[]{new EmojiFilter()});
    }
}
