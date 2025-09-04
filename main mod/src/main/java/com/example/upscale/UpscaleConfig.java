package com.example.upscale;

import com.example.upscale.util.Debug;

public class UpscaleConfig {
    // Public for legacy compatibility (Option A)
    public static float scale = 1.0f;       // 1.0 = native
    public static boolean debugOverlay = false;
    public static boolean enabled = true;   // master toggle

    // ----- Modern accessors -----
    public static float getScale() { return scale; }
    public static boolean isEnabled() { return enabled; }
    public static boolean isDebug() { return debugOverlay; }

    // ----- Mutators -----
    public static void setScale(double s) {
        float clamped = (float) Math.max(0.1, Math.min(1.0, s));
        scale = clamped;
        System.out.println("[MCScaler] Scale updated to " + scale);
    }

    public static void setDebug(boolean debug) {
        debugOverlay = debug;
        try {
            Debug.set(debug);
        } catch (Throwable ignored) {}
        System.out.println("[MCScaler] Debug overlay set to " + debug);
    }

    public static void setEnabled(boolean val) {
        enabled = val;
        System.out.println("[MCScaler] Upscale enabled set to " + val);
    }
}
