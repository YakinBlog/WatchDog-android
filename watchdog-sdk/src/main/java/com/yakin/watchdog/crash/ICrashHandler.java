package com.yakin.watchdog.crash;

public interface ICrashHandler {

    void registerHandler(ICatchHandler handler);

    void unregisterHandler();
}
