package com.example.upscale;

import com.example.upscale.util.Debug;

public class UpscaleConfig {
    public static float scale = 1.0f; // default full res
    public static boolean debugOverlay = false;
    public static boolean enabled = true; // master toggle

    public static void setScale(double s) {
        float clamped = (float)Math.max(0.1, Math.min(1.0, s));
        scale = clamped;
        System.out.println("[MCScaler] Scale updated to " + scale);
    }

    public static void setDebug(boolean debug) {
        debugOverlay = debug;
        Debug.set(debug);
        System.out.println("[MCScaler] Debug overlay set to " + debug);
    }

    public static boolean isDebug() {
        return debugOverlay;
    }

    public static void setEnabled(boolean val) {
        enabled = val;
        System.out.println("[MCScaler] Upscale enabled set to " + val);
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
