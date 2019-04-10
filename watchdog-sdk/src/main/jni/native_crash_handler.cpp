#include "native_crash_handler.h"

JNIEnv *g_env;
jclass g_bridge;
jmethodID g_method;

void sigaction_callback(int signal) {
    char *reason = strsignal(signal);
    char *stack = get_backtrace(1024*5, 100);
    LOGD("reason:%s\n backtrace:\n%s", reason, stack);
    g_env->CallVoidMethod(g_bridge, g_method, gettid(), g_env->NewStringUTF(reason), g_env->NewStringUTF(stack));
}

JNIEXPORT void JNICALL Java_com_yakin_watchdog_crash_JNIBridge_registerNativeCrash(JNIEnv *env, jobject clazz) {
	LOGD("registerNativeCrash was called");
    g_env = env;
    g_bridge = (jclass) env->NewGlobalRef(clazz);
    jclass bridge = env->FindClass("com/yakin/watchdog/crash/JNIBridge");
    g_method = env->GetMethodID(bridge, "uncaughtException", "(ILjava/lang/String;Ljava/lang/String;)V");
    if(g_method == NULL) {
        LOGE("Mathod not found(uncaughtException(int tid, String reason, String stack))");
        return;
    }
    create_signal_handler(sigaction_callback);
}

JNIEXPORT void JNICALL Java_com_yakin_watchdog_crash_JNIBridge_unregisterNativeCrash(JNIEnv *env, jobject clazz) {
    LOGD("unregisterNativeCrash was called");
    destroy_signal_handler();
    if(g_env != NULL) {
        g_env -> DeleteGlobalRef(g_bridge);
        g_env = NULL;
    }
}