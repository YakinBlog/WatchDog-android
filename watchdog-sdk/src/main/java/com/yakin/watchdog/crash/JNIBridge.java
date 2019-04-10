package com.yakin.watchdog.crash;

import com.yakin.watchdog.WatchDog;
import com.yakin.watchdog.utils.LibUtil;
import com.yakin.watchdog.utils.ThreadUtil;

class JNIBridge {

    static {
        LibUtil.loadLibrary("watchdog");
    }

    private ICatchHandler catchHandler;

    public void setHandler(ICatchHandler handler) {
        this.catchHandler = handler;
    }

    public void uncaughtException(int tid, String reason, String stack){
        Thread thread = ThreadUtil.getThreadById(tid);
        StringBuilder stackTraceBuilder = new StringBuilder();
        stackTraceBuilder.append("\nThread: ").append(thread.getName());
        stackTraceBuilder.append("\nReason: ").append(reason);
        stackTraceBuilder.append("\n===================Native StackTrace===================\n");
        stackTraceBuilder.append(stack);
        stackTraceBuilder.append("\n===================Java StackTrace===================\n");
        stackTraceBuilder.append(ThreadUtil.getStackTrace(new Throwable(), JNIBridge.class));
        String stackTrace = stackTraceBuilder.toString();
        WatchDog.getLogger().e(stackTrace);
        if(catchHandler != null) {
            catchHandler.onCatch(tid, reason, stackTrace);
        }
    }

    public native void registerNativeCrash();
    public native void unregisterNativeCrash();
}
