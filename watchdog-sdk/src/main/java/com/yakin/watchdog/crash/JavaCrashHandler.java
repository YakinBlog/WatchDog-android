package com.yakin.watchdog.crash;

import com.yakin.watchdog.WatchDog;
import com.yakin.watchdog.utils.ThreadUtil;

class JavaCrashHandler implements ICrashHandler, Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler handler;
    private ICatchHandler catchHandler;

    @Override
    public void registerHandler(ICatchHandler handler) {
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if(exceptionHandler instanceof JavaCrashHandler) {
            WatchDog.getLogger().i("The catcher has been registered");
            return;
        }
        this.catchHandler = handler;
        this.handler = exceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void unregisterHandler() {
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        stackTraceBuilder.append("\nThread: ").append(t.getName());
        stackTraceBuilder.append("\nReason: ").append(e.getLocalizedMessage());
        stackTraceBuilder.append("\n===================Java StackTrace===================\n");
        stackTraceBuilder.append(ThreadUtil.getStackTrace(e, JavaCrashHandler.class));
        String stackTrace = stackTraceBuilder.toString();
        WatchDog.getLogger().e(e, stackTrace);
        if(catchHandler != null) {
            catchHandler.onCatch(t.getId(), e.getLocalizedMessage(), stackTrace);
        }
        if(handler != null) {
            handler.uncaughtException(t, e);
        }
    }
}
