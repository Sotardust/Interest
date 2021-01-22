package com.dht.interest.login;


import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiFilter implements InputFilter {

    private static final String TAG = "EmojiFilter";

    private String regext = "[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\ud83e\\udc00-\\ud83e\\udfff]|[\\u2500-\\u29ff]|[\\u20d0-\\u20f0]|[\\u2b00-\\u2bff]|[\\u3297-\\u3299]|[\\u2300-\\u23ff]|[\\u2190-\\u21ff]|[\\u24b6-\\u24e9]\n";
    private Pattern mEmojiPattern = Pattern.compile(regext, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = mEmojiPattern.matcher(source);

        if (emojiMatcher.find()) {
            Log.d(TAG, "source: " + source.toString() + " is match.");
            return "";
        }
        return source;
    }


}

