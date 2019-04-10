package com.yakin.watchdog.utils;

public final class ThreadUtil {

    public static Thread getThreadById(long tid) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(tid == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return Thread.currentThread();
    }

    public static String getStackTrace(Throwable throwable, Class clazz) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (StackTraceElement stackTrace: stackTraceElements) {
            String className = stackTrace.getClassName();
            if (!className.equals(clazz.getName())) {
                stackTraceBuilder.append(stackTrace.getClassName());
                stackTraceBuilder.append(".");
                stackTraceBuilder.append(stackTrace.getMethodName());
                stackTraceBuilder.append("(");
                stackTraceBuilder.append(stackTrace.getLineNumber());
                stackTraceBuilder.append(")");
                stackTraceBuilder.append("\n");
            }
        }
        return stackTraceBuilder.toString();
    }
}
