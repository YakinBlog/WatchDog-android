#ifndef CAPTURE_BACKTRACE_H
#define CAPTURE_BACKTRACE_H

#include <unwind.h>
#include <dlfcn.h>
#include <string.h>
#include <stdio.h>

#include "global_header.h"

#ifdef __cplusplus
extern "C" {
#endif

extern void dump_backtrace(char* buffer, size_t maxDeep);

#ifdef __cplusplus
}
#endif
#endif //CAPTURE_BACKTRACE_H
