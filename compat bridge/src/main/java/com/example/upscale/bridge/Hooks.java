package com.example.upscale.bridge;

import com.example.upscale.util.Debug;

public final class Hooks {
    private static final String MAIN_RENDER_SCALER = "com.example.upscale.RenderScaler";
    private static final String SODIUM_RENDERER = "net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer";
    private static final String IRIS_PIPELINE = "net.coderbot.iris.pipeline.WorldRenderingPipeline";
    private static Boolean mainPresent = null;

    private Hooks() {}

    private static boolean hasMain() {
        if (mainPresent != null) return mainPresent.booleanValue();
        try {
            Class.forName(MAIN_RENDER_SCALER);
            mainPresent = Boolean.TRUE;
        } catch (Throwable t) {
            mainPresent = Boolean.FALSE;
        }
        return mainPresent.booleanValue();
    }

    private static boolean hasRenderer() {
        try { Class.forName(SODIUM_RENDERER); return true; } catch (Throwable ignored) {}
        try { Class.forName(IRIS_PIPELINE); return true; } catch (Throwable ignored) {}
        return false;
    }

    public static void registerStandalone() {
        Debug.log("CompatBridge", "registering standalone hooks");
        // nothing else to do
    }

    public static void registerRelay() {
        Debug.log("CompatBridge", "registering relay hooks");
        boolean renderer = hasRenderer();
        Debug.log("CompatBridge", "detected renderer=" + renderer);
        if (!hasMain()) {
            Debug.log("CompatBridge", "main scaler not present; relay will no-op");
            return;
        }
        try {
            call(MAIN_RENDER_SCALER, "setBridgeDriving", new Class[]{boolean.class}, renderer);
        } catch (Throwable t) {
            Debug.log("CompatBridge", "failed to set bridge driving flag: " + t);
        }
    }

    // Called by Sodium/Iris mixins
    public static void beforeWorldRender() {
        if (!hasMain()) return;
        try {
            call(MAIN_RENDER_SCALER, "beginWorldLowRes", new Class[]{String.class}, "CompatBridge BEFORE");
        } catch (Throwable t) {
            Debug.log("CompatBridge", "relay before failed: " + t);
        }
    }

    public static void afterWorldRender() {
        if (!hasMain()) return;
        try {
            call(MAIN_RENDER_SCALER, "endWorldAndUpscaleToMain", new Class[]{String.class}, "CompatBridge AFTER");
            call(MAIN_RENDER_SCALER, "ensureClosedIfOpen", new Class[]{String.class}, "CompatBridge AFTER ensure");
        } catch (Throwable t) {
            Debug.log("CompatBridge", "relay after failed: " + t);
        }
    }

    private static void call(String clazz, String method, Class<?>[] params, Object... args) throws Exception {
        Class<?> c = Class.forName(clazz);
        if (params == null) params = new Class<?>[0];
        c.getMethod(method, params).invoke(null, args);
    }
}
