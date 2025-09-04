package com.example.upscale.bridge;

import net.fabricmc.api.ClientModInitializer;

public class CompatBridge implements ClientModInitializer {
    static boolean mainPresent;

    static {
        boolean present = false;
        try {
            Class.forName("com.example.upscale.RenderScaler");
            present = true;
            System.out.println("[MCScaler Bridge] Main present; bridge passive.");
        } catch (Throwable t) {
            System.out.println("[MCScaler Bridge] Main NOT present; bridge active.");
        }
        mainPresent = present;
    }

    @Override
    public void onInitializeClient() {
        if (!mainPresent) {
            Hooks.registerStandalone(); // bridge does its own downscale when main is absent
        } else {
            Hooks.registerRelay();      // relay only: call into main at stable points
        }
    }
}
