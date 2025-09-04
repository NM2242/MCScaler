package com.example.upscale.util;
public final class Debug {
    private Debug(){}
    public static boolean enabled() {
        try {
            Class<?> c1 = Class.forName("com.example.upscale.config.UpscaleConfig");
            java.lang.reflect.Field f = c1.getDeclaredField("debug");
            f.setAccessible(true);
            return f.getBoolean(null);
        } catch (Throwable t) {
            try {
                Class<?> c2 = Class.forName("com.example.upscale.util.UpscaleConfig");
                java.lang.reflect.Field f2 = c2.getDeclaredField("debug");
                f2.setAccessible(true);
                return f2.getBoolean(null);
            } catch (Throwable t2) {
                return false;
            }
        }
    }
    public static void log(String tag, String msg) {
        if (enabled()) System.out.println(tag + " " + msg);
    }
}