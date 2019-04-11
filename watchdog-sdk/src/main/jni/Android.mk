LOCAL_PATH := $(call my-dir)

# Hide internal symbol table
cmd-strip = $(TOOLCHAIN_PREFIX)strip --strip-all -x $1

include $(CLEAR_VARS)

# An efficient way to reduce binary size is to use the visibility feature of gcc
LOCAL_CPPFLAGS += -fvisibility=hidden
LOCAL_CFLAGS += -fvisibility=hidden

# Discard Unused Functions with gc-sections
LOCAL_CPPFLAGS += -ffunction-sections -fdata-sections
LOCAL_CFLAGS += -ffunction-sections -fdata-sections
# LOCAL_LDFLAGS += -Wl,--gc-sections

# Do not use Exceptions and RTTI
LOCAL_CPPFLAGS += -fexceptions -frtti

# Remove Duplicated Code
#ifeq ($(TARGET_ARCH),mips)
  LOCAL_LDFLAGS += -Wl,--gc-sections
#else
#  LOCAL_LDFLAGS += -Wl,--gc-sections,--icf=safe
#endif

LOCAL_MODULE := watchdog

LOCAL_SRC_FILES := \
    signal/capture_backtrace.c \
    signal/signal_catch.c \
    native_crash_handler.cpp \

LOCAL_LDLIBS := -llog

# COFFEE dump stack
LOCAL_CFLAGS += -funwind-tables -Wl,--no-merge-exidx-entries

include $(BUILD_SHARED_LIBRARY)