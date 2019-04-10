package com.yakin.watchdog.crash;

public class CrashHandlerImpl implements ICrashHandler {

    private ICrashHandler javaCrashHandler;
    private ICrashHandler nativeCrashHandler;

    public CrashHandlerImpl() {
        javaCrashHandler = new JavaCrashHandler();
        nativeCrashHandler = new NativeCrashHandler();
    }

    @Override
    public void registerHandler(ICatchHandler handler) {
        javaCrashHandler.registerHandler(handler);
        nativeCrashHandler.registerHandler(handler);
    }

    @Override
    public void unregisterHandler() {
        javaCrashHandler.unregisterHandler();
        nativeCrashHandler.unregisterHandler();
    }
}
