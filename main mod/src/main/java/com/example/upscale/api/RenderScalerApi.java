package com.example.upscale.api;

import com.example.upscale.UpscaleConfig;

public final class RenderScalerApi {
    private RenderScalerApi() {}

    public static void setScale(double scale01) {
        UpscaleConfig.setScale(scale01);
    }

    public static double getScale() {
        return UpscaleConfig.scale;
    }

    public static void setDebugOverlay(boolean on) {
        UpscaleConfig.setDebug(on);
    }

    public static boolean isDebugOverlay() {
        return UpscaleConfig.debugOverlay;
    }
}
