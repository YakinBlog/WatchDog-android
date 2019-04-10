package com.yakin.watchdog.utils;

import android.content.Context;

import com.yakin.watchdog.WatchDog;

import java.lang.reflect.Method;

public final class ContextUtil {

    private static Context sAppContext;

    /**
     * @return Application's context
     */
    public static Context getContext() {
        if (sAppContext == null) {
            synchronized (ContextUtil.class) {
                if (sAppContext == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread); //获取 currentActivityThread 对象

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        sAppContext =(Context) method2.invoke(currentActivityThread); //获取 Context 对象
                    } catch (Exception e) {
                        WatchDog.getLogger().e(e, "getApplication failed");
                    }
                }
            }
        }
        return sAppContext;
    }
}
