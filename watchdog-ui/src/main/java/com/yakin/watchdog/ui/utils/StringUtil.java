package com.yakin.watchdog.ui.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class StringUtil {

    public static SpannableString foregroundColorSpan(String text, int color) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, text.length(), 0);
        return spannableString;
    }
}
