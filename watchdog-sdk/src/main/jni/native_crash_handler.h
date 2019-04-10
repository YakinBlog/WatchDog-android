#ifndef _com_yakin_watchdog_crash_JNIBridge_
#define _com_yakin_watchdog_crash_JNIBridge_

#include <zconf.h>

#include "global_header.h"
#include "signal/signal_catch.h"
#include "signal/capture_backtrace.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_yakin_watchdog_crash_JNIBridge_registerNativeCrash(JNIEnv *, jobject);

JNIEXPORT void JNICALL Java_com_yakin_watchdog_crash_JNIBridge_unregisterNativeCrash(JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif //_com_yakin_watchdog_crash_JNIBridge_
