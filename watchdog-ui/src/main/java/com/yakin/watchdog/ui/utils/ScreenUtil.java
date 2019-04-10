package com.yakin.watchdog.ui.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class ScreenUtil {

    /**
     * 隐藏ActionBar
     */
    @SuppressLint("RestrictedApi")
    public static void hideActionBar(Context context) {
        AppCompatActivity appCompatActivity = ContextUtil.scanAppCompatActivity(context);
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null && actionBar.isShowing()) {
                actionBar.setShowHideAnimationEnabled(false);
                actionBar.hide();
            }
        }
    }

    /**
     * 显示ActionBar
     */
    @SuppressLint("RestrictedApi")
    public static void showActionBar(Context context) {
        AppCompatActivity appCompatActivity = ContextUtil.scanAppCompatActivity(context);
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null && !actionBar.isShowing()) {
                actionBar.setShowHideAnimationEnabled(false);
                actionBar.show();
            }
        }
    }

    /**
     * 设置沉浸式状态栏
     * @param context
     */
    public static void setImmersiveStatusBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity activity = ContextUtil.scanForActivity(context);
            View decorView = activity.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
                    WindowInsets defaultInsets = view.onApplyWindowInsets(insets);
                    return defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.getSystemWindowInsetLeft(),
                            0,
                            defaultInsets.getSystemWindowInsetRight(),
                            defaultInsets.getSystemWindowInsetBottom());
                }
            });
            ViewCompat.requestApplyInsets(decorView);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    /**
     * 设置状态栏颜色
     * @param context
     * @param colorId
     */
    public static void setStatusBarColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity activity = ContextUtil.scanForActivity(context);
            View decorView = activity.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                    return defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.getSystemWindowInsetLeft(),
                            defaultInsets.getSystemWindowInsetTop(),
                            defaultInsets.getSystemWindowInsetRight(),
                            defaultInsets.getSystemWindowInsetBottom());
                }
            });
            ViewCompat.requestApplyInsets(decorView);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, colorId));
        }
    }

    /**
     * 设置工具栏蓝色
     * @param context
     * @param colorId
     */
    public static void setNavigationBarColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity activity = ContextUtil.scanForActivity(context);
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(context, colorId));
        }
    }
}
