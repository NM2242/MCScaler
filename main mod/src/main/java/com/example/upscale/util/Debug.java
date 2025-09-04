package com.example.upscale.util;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class Debug {
    private static final AtomicBoolean ENABLED = new AtomicBoolean(false);
    private static final AtomicBoolean OVERLAY = new AtomicBoolean(true);
    private static final DecimalFormat DF = new DecimalFormat("0.00");
    private static volatile double lastScale = 1.0;
    private static volatile boolean lastActive = false;
    private static final AtomicLong frame = new AtomicLong();

    public static void set(boolean on){ ENABLED.set(on); }
    public static boolean isEnabled(){ return ENABLED.get(); }

    public static void log(String tag, String msg){
        if(!ENABLED.get()) return;
        System.out.println("[MCScaler][" + tag + "] " + msg);
    }

    // Overlay helpers
    public static void setOverlay(boolean on){ OVERLAY.set(on); }
    public static boolean overlay(){ return OVERLAY.get(); }

    public static void setState(double scale, boolean active){
        lastScale = scale;
        lastActive = active;
    }

    public static long nextFrame(){
        return frame.incrementAndGet();
    }

    public static String overlayLine(){
        return "MCScaler scale=" + DF.format(lastScale)
               + " active=" + lastActive
               + " frame=" + frame.get();
    }
}
