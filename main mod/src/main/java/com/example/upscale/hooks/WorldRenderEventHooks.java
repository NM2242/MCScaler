package com.example.upscale.hooks;

import com.example.upscale.RenderScaler;

public class WorldRenderEventHooks {
    public static void beforeWorldRender() {
        if (com.example.upscale.RenderScaler.isBridgeDriving()) return;
        RenderScaler.beginWorldLowRes("CompatBridge beforeWorldRender");
    }

    public static void afterWorldRender() {
        if (com.example.upscale.RenderScaler.isBridgeDriving()) return;
        RenderScaler.endWorldAndUpscaleToMain("CompatBridge afterWorldRender");
    }
}
