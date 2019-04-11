#include "signal_catch.h"

struct sigaction old_sa[NSIG];
const int monitored_signals[] = {
        SIGILL, //4 非法指令
        SIGABRT, //6 来自abort函数的终止信号
        SIGBUS, //7 总线错误
        SIGFPE,  //8 浮点运算溢出
        SIGSEGV,  //11 无效的存储器引用,进程试图去访问其虚地址空间以外的位置
        SIGPIPE, //13 向一个没有读用户的管道做写操作
        SIGSTKFLT, //16 协处理器上的栈故障
        SIGSYS, //31 系统调用中参数错
        SIGTRAP //5 启动进程，跟踪代码的执行
};

void(*sigaction_callback)(int signal);

void internal_sigaction_callback(int signal, struct siginfo *siginfo, void *sigcontext) {
    LOGD("internal_sigaction_callback was called:%d", signal);

    // 兼容其他signal处理
    // 某些信号可能在之前已经被安装过信号处理函数，而sigaction一个信号量只能注册
    // 一个处理函数，这意味着我们的处理函数会覆盖其他人的处理信号。
    // 保存旧的处理函数，在处理完我们的信号处理函数后，在重新运行老的处理函数就能完成兼容。
    if(old_sa[signal].sa_sigaction) {
        old_sa[signal].sa_sigaction(signal, siginfo, sigcontext);
    }

    sigaction_callback(signal);
}

void create_signal_handler(void(*callback)(int)) {
    LOGD("create_signal_handler was called");

    sigaction_callback = callback;
    // 注册信号处理回调函数
    struct sigaction sa;
    memset(&sa, 0, sizeof(sa));
    sigemptyset(&sa.sa_mask);
    sa.sa_sigaction = internal_sigaction_callback;
    // 信号处理之后重新设置为默认的处理方式。
    //    SA_RESTART：使被信号打断的syscall重新发起，不设置此方式无法反射调用java函数。
    //    SA_NOCLDSTOP：使父进程在它的子进程暂停或继续运行时不会收到 SIGCHLD 信号。
    //    SA_NOCLDWAIT：使父进程在它的子进程退出时不会收到SIGCHLD信号，这时子进程如果退出也不会成为僵 尸进程。
    //    SA_NODEFER：使对信号的屏蔽无效，即在信号处理函数执行期间仍能发出这个信号。
    //    SA_RESETHAND：信号处理之后重新设置为默认的处理方式。
    //    SA_SIGINFO：使用sa_sigaction成员而不是sa_handler作为信号处理函数。
    sa.sa_flags = SA_SIGINFO | SA_RESTART;

    // 设置额外栈空间
    // SIGSEGV很有可能是栈溢出引起的，如果在默认的栈上运行很有可能会破坏程序运行的现场，
    // 无法获取到正确的上下文。而且当栈满了（太多次递归，栈上太多对象），系统会在同一个
    // 已经满了的栈上调用SIGSEGV的信号处理函数，又再一次引起同样的信号。
    // 我们应该开辟一块新的空间作为运行信号处理函数的栈。可以使用sigaltstack在任意线程
    // 注册一个可选的栈，保留一下在紧急情况下使用的空间。（系统会在危险情况下把栈指针指
    // 向这个地方，使得可以在一个新的栈上运行信号处理函数）
    stack_t stack;
    memset(&stack, 0, sizeof(stack));
    stack.ss_size = SIGSTKSZ;
    stack.ss_sp = malloc(stack.ss_size);
    if(!stack.ss_sp) {
        LOGE("Could not allocate signal alternative stack");
    }
    stack.ss_flags = 0;
    if(sigaltstack(&stack, NULL)) {
        LOGE("Could not set signal stack");
    }

    for (unsigned i = 0; i < sizeof(monitored_signals) / sizeof(int); ++i) {
        const int s = monitored_signals[i];
        if(sigaction(s, &sa, &old_sa[s])) {
            LOGE("Could not register signal callback for [%d]", s);
        }
    }
}

void destroy_signal_handler() {
    LOGD("destroy_signal_handler was called");

    // Uninstall the signal handlers and restore their old actions.
    for (unsigned i = 0; i < sizeof(monitored_signals) / sizeof(int); ++i) {
        const int s = monitored_signals[i];
        sigaction(s, &old_sa[s], NULL);
    }
}