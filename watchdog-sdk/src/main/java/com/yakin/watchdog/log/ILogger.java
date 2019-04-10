package com.yakin.watchdog.log;

public interface ILogger {

    int NONE = 0;
    int ERROR = NONE + 1;
    int WARN = NONE + 2;
    int INFO = NONE + 3;
    int DEBUG = NONE + 4;

    void setPrintLog(int level, String tag);

    void e(String format, Object... args);

    void e(Throwable e, String format, Object... args);

    void w(String format, Object... args);

    void w(Throwable e, String format, Object... args);

    void i(String format, Object... args);

    void i(Throwable e, String format, Object... args);

    void d(String format, Object... args);

    void d(Throwable e, String format, Object... args);
}
