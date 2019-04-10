package com.yakin.watchdog.crash;

public interface ICatchHandler {

    void onCatch(long tid, String reason, String stackTrace);
}
