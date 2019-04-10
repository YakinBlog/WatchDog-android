package com.yakin.watchdog.crash;

class NativeCrashHandler implements ICrashHandler {

    private JNIBridge jniBridge;

    public NativeCrashHandler() {
        jniBridge = new JNIBridge();
    }

    @Override
    public void registerHandler(ICatchHandler handler) {
        jniBridge.setHandler(handler);
        jniBridge.registerNativeCrash();
    }

    @Override
    public void unregisterHandler() {
        jniBridge.unregisterNativeCrash();
    }
}
