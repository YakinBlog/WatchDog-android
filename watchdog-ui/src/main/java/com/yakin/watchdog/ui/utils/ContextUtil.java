package com.yakin.watchdog.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import com.yakin.watchdog.WatchDog;

import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

public final class ContextUtil {

    private static Context sContext;

    /**
     * @return Application's context
     */
    public static Context getContext() {
        if (sContext == null) {
            synchronized (ContextUtil.class) {
                if (sContext == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread); //获取 currentActivityThread 对象

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        sContext =(Context) method2.invoke(currentActivityThread); //获取 Context 对象
                    } catch (Exception e) {
                        WatchDog.getLogger().e(e, "getContext failed");
                    }
                }
            }
        }
        return sContext;
    }

    /**
     * @return Activity from context
     */
    public static Activity scanForActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * @return AppCompatActivity from context
     */
    public static AppCompatActivity scanAppCompatActivity(Context context) {
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return scanAppCompatActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }
}
