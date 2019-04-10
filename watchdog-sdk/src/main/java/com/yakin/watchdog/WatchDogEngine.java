package com.yakin.watchdog;

import com.yakin.watchdog.crash.CrashHandlerImpl;
import com.yakin.watchdog.crash.ICrashHandler;
import com.yakin.watchdog.log.ILogger;
import com.yakin.watchdog.log.LoggerImpl;

class WatchDogEngine {

    private static class LogSingleton {
        private static ILogger Logger = new LoggerImpl();
    }

    public ILogger getLogger() {
        return LogSingleton.Logger;
    }

    private static class CrashSingleton {
        private static ICrashHandler Handler = new CrashHandlerImpl();
    }

    public ICrashHandler getCrashHandler() {
        return CrashSingleton.Handler;
    }
}
