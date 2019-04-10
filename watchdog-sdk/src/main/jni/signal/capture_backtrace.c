#include "capture_backtrace.h"

struct BacktraceState {
    intptr_t* current;
    intptr_t* end;
};

_Unwind_Reason_Code unwind_callback(struct _Unwind_Context* context, void* arg) {
    struct BacktraceState* state = (struct BacktraceState*)(arg);
    intptr_t ip = (intptr_t)_Unwind_GetIP(context);
    if (ip) {
        if (state->current == state->end) {
            return _URC_END_OF_STACK;
        } else {
            state->current[0] = ip;
            state->current++;
        }
    }
    return _URC_NO_REASON;
}

size_t capture_backtrace(intptr_t* buffer, size_t maxStackDeep) {
    struct BacktraceState state = {buffer, buffer + maxStackDeep};
    _Unwind_Backtrace(unwind_callback, &state);
    return state.current - buffer;
}

void dump_backtrace(char *out, intptr_t* buffer, size_t count) {
    for (size_t idx = 0; idx < count; ++idx) {
        intptr_t addr = buffer[idx];
        const char* symbol = "      ";
        const char* dlfile="      ";

        Dl_info info;
        if (dladdr((void*)addr, &info)) {
            if(info.dli_sname){
                symbol = info.dli_sname;
            }
            if(info.dli_fname){
                dlfile = info.dli_fname;
            }
        } else {
            strcat(out,"#                               \n");
            continue;
        }
        char temp[50];
        memset(temp,0,sizeof(temp));
        sprintf(temp,"%zu",idx);
        strcat(out,"#");
        strcat(out,temp);
        strcat(out, ": ");
        memset(temp,0,sizeof(temp));
        sprintf(temp,"%zu",addr);
        strcat(out,temp);
        strcat(out, "  " );
        strcat(out, symbol);
        strcat(out, "      ");
        strcat(out, dlfile);
        strcat(out, "\n" );
    }
}

char* get_backtrace(size_t maxLength, size_t maxDeep) {
    char outBuffer[maxLength];
    memset(outBuffer, 0, sizeof(outBuffer));
    intptr_t stackBuffer[maxDeep];

    size_t count = capture_backtrace(stackBuffer, maxDeep);
    dump_backtrace(outBuffer, stackBuffer, count);
    return outBuffer;
}