package com.yakin.watchdog.ui.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class DensityUtil {

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }
}
