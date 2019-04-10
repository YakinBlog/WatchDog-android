package com.yakin.watchdog.log;

import android.util.Log;

import java.util.Locale;

public class LoggerImpl implements ILogger {

    private int level;
    private String tag;

    @Override
    public void setPrintLog(int level, String tag) {
        this.level = level;
        this.tag = tag;
    }

    @Override
    public void e(String format, Object... args) {
        if(level > ILogger.NONE) {
            Log.e(tag, buildLocaleMessage(format, args));
        }
    }

    @Override
    public void e(Throwable e, String format, Object... args) {
        if(level > ILogger.NONE) {
            Log.e(tag, buildLocaleMessage(format, args), e);
        }
    }

    @Override
    public void w(String format, Object... args) {
        if(level > ILogger.ERROR) {
            Log.w(tag, buildLocaleMessage(format, args));
        }
    }

    @Override
    public void w(Throwable e, String format, Object... args) {
        if(level > ILogger.ERROR) {
            Log.w(tag, buildLocaleMessage(format, args), e);
        }
    }

    @Override
    public void i(String format, Object... args) {
        if(level > ILogger.WARN) {
            Log.i(tag, buildLocaleMessage(format, args));
        }
    }

    @Override
    public void i(Throwable e, String format, Object... args) {
        if(level > ILogger.WARN) {
            Log.i(tag, buildLocaleMessage(format, args), e);
        }
    }

    @Override
    public void d(String format, Object... args) {
        if(level > ILogger.INFO) {
            Log.d(tag, buildLocaleMessage(format, args));
        }
    }

    @Override
    public void d(Throwable e, String format, Object... args) {
        if(level > ILogger.INFO) {
            Log.d(tag, buildLocaleMessage(format, args), e);
        }
    }

    private static String buildLocaleMessage(String format, Object... args) {
        String msg = (args == null || args.length < 1) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        for (StackTraceElement stack : trace) {
            String clazzName = stack.getClassName();
            if (!clazzName.equals(LoggerImpl.class.getName())) {
                clazzName = clazzName.substring(clazzName.lastIndexOf('.') + 1);

                caller = clazzName + "." + stack.getMethodName() + " (" + stack.getLineNumber() + ")";
                break;
            }
        }

        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }
}
