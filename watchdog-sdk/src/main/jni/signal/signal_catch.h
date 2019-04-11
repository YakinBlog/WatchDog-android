#ifndef SIGNAL_CATCH_H
#define SIGNAL_CATCH_H

#include <signal.h>
#include <malloc.h>
#include <string.h>

#include "global_header.h"

#ifdef __cplusplus
extern "C" {
#endif

extern void create_signal_handler(void(*callback)(int));

extern void destroy_signal_handler();

#ifdef __cplusplus
}
#endif
#endif //SIGNAL_CATCH_H
