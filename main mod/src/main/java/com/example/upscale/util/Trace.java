package com.example.upscale.util;

public final class Trace {
    private Trace() {}
    public static void t(String tag, String msg) {
        try {
            Debug.log(tag, msg);
        } catch (Throwable t) {
            System.out.println("[" + tag + "] " + msg);
        }
    }
}
