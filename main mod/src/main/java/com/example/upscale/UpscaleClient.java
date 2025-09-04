package com.example.upscale;

import net.fabricmc.api.ClientModInitializer;

public class UpscaleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[MCScaler] UpscaleClient initialized");
        // WorldRenderEventHooks.register(); <-- REMOVE (no longer needed, mixins handle wrapping)
    }
}
