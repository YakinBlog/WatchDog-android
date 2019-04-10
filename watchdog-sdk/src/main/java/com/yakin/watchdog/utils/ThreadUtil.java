package com.yakin.watchdog.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

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

    public static String getStackTrace(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }
}
