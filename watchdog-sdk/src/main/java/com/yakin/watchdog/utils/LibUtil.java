package com.yakin.watchdog.utils;

import android.util.SparseBooleanArray;

import com.yakin.watchdog.WatchDog;

public final class LibUtil {

    private static SparseBooleanArray sLoadedLibrary = new SparseBooleanArray();

    public static boolean loadLibrary(String soFile) {
        final int hashCode = soFile.hashCode();
        if(!sLoadedLibrary.get(hashCode)) {
            try {
                long loadTime = System.currentTimeMillis();
                System.loadLibrary(soFile);
                WatchDog.getLogger().d("load library time is %dms.", System.currentTimeMillis() - loadTime);
                sLoadedLibrary.put(hashCode, true);
            } catch (UnsatisfiedLinkError e) {
                WatchDog.getLogger().e("%s.so load failed.", soFile);
            }
        }
        return sLoadedLibrary.get(hashCode);
    }
}
