package com.yakin.watchdog;

import com.yakin.watchdog.crash.ICrashHandler;
import com.yakin.watchdog.log.ILogger;

public class WatchDog {

    private WatchDog() {}

    private static class Singleton {
        private static WatchDogEngine Engine = new WatchDogEngine();
    }

    public static ILogger getLogger() {
        return Singleton.Engine.getLogger();
    }

    public static ICrashHandler getCrashHandler() {
        return Singleton.Engine.getCrashHandler();
    }
}
