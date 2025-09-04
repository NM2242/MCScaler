package com.example.upscale;

import com.example.upscale.util.Debug;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;

public class RenderScaler {
    private static RenderScaler INSTANCE;

    private Framebuffer lowResFbo;
    private boolean active = false;

    private RenderScaler() {}

    public static RenderScaler get() {
        if (INSTANCE == null) {
            INSTANCE = new RenderScaler();
        }
        return INSTANCE;
    }

    // --- Old bridge compatibility stubs (safe no-ops) ---
    public static boolean isBridgeDriving() { return false; }
    public static void beginWorldLowRes(String tag) {}
    public static void endWorldAndUpscaleToMain(String tag) {}

    public static void beginLowResIfActive() {
        RenderScaler self = get();
        if (!UpscaleConfig.enabled) return;

        float scale = UpscaleConfig.getScale();
        if (scale >= 0.999f) {
            self.active = false;
            return;
        }

        MinecraftClient mc = MinecraftClient.getInstance();
        int ww = mc.getWindow().getFramebufferWidth();
        int wh = mc.getWindow().getFramebufferHeight();
        int lw = Math.max(1, (int)(ww * scale));
        int lh = Math.max(1, (int)(wh * scale));

        if (self.lowResFbo == null || self.lowResFbo.textureWidth != lw || self.lowResFbo.textureHeight != lh) {
            if (self.lowResFbo != null) {
                self.lowResFbo.delete();
            }
            self.lowResFbo = new SimpleFramebuffer(lw, lh, true, MinecraftClient.IS_SYSTEM_MAC);
            Debug.log("MCScaler", "Created lowResFbo " + lw + "x" + lh);
        }

        self.lowResFbo.beginWrite(true);
        self.active = true;
    }

    public static void endLowResAndUpscale() {
        RenderScaler self = get();
        if (!self.active || self.lowResFbo == null) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        int ww = mc.getWindow().getFramebufferWidth();
        int wh = mc.getWindow().getFramebufferHeight();

        mc.getFramebuffer().beginWrite(true);

        // ✅ correct call for 1.20.1
        self.lowResFbo.draw(ww, wh, false);

        self.active = false;
        Debug.log("MCScaler", "Upscaled lowResFbo → full window " + ww + "x" + wh);
    }

    public static void tick() {
        // Could be used for async cleanup or advanced pipelines later
    }
}
