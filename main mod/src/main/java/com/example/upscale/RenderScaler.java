package com.example.upscale;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

/**
 * Central scaler: wraps world rendering into a low-res framebuffer and upscales to the window.
 */
public final class RenderScaler {
    private static SimpleFramebuffer lowFbo;
    private static int lowW = -1, lowH = -1;
    private static boolean active = false;
    private static boolean bridgeDriving = false;

    private RenderScaler(){}

    public static boolean isActive() { return active; }
    public static boolean isBridgeDriving() { return bridgeDriving; }
    public static void setBridgeDriving(boolean driving) { bridgeDriving = driving; }

    private static void log(String s) {
        com.example.upscale.util.Debug.log("[MCScaler Main] ", s);
    }

    private static void ensureLowFbo(int ww, int wh) {
        int targetW = Math.max(1, Math.round(ww * UpscaleConfig.scale));
        int targetH = Math.max(1, Math.round(wh * UpscaleConfig.scale));
        if (lowFbo == null || targetW != lowW || targetH != lowH) {
            if (lowFbo != null) {
                lowFbo.delete();
            }
            lowFbo = new SimpleFramebuffer(targetW, targetH, true, MinecraftClient.IS_SYSTEM_MAC);
            lowW = targetW; lowH = targetH;
            log("Created low FBO " + lowW + "x" + lowH + " for window " + ww + "x" + wh + " scale=" + UpscaleConfig.scale);
        }
    }

    /** Begin low-res capture. No-op if disabled or scale ~1. */
    public static boolean beginWorldLowRes(String from) {
        if (!UpscaleConfig.enabled) return false;
        if (UpscaleConfig.scale >= 0.999f) return false;
        var mc = MinecraftClient.getInstance();
        int ww = mc.getWindow().getFramebufferWidth();
        int wh = mc.getWindow().getFramebufferHeight();
        ensureLowFbo(ww, wh);

        // Bind low-res FBO
        lowFbo.beginWrite(true);
        RenderSystem.viewport(0, 0, lowW, lowH);
        active = true;
        log("BEGIN low-res from=" + from + " -> " + lowW + "x" + lowH);
        return true;
    }

    /** End capture and upscale to main framebuffer. Safe to call even if not active. */
    public static void endWorldAndUpscaleToMain(String from) {
        if (!active) return;
        var mc = MinecraftClient.getInstance();
        Framebuffer main = mc.getFramebuffer();

        // Finish writing to low FBO
        lowFbo.endWrite();
        // Bind main for drawing and draw a full-screen quad sampling lowFbo color texture.
        main.beginWrite(true);

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, lowFbo.getColorAttachment());

        // Account for GL texture size if not exact
        float u = (float) lowW / (float) lowFbo.textureWidth;
        float v = (float) lowH / (float) lowFbo.textureHeight;

        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        // NDC quad
        buf.vertex(-1, -1, 0).texture(0, 0).next();
        buf.vertex( 1, -1, 0).texture(u, 0).next();
        buf.vertex( 1,  1, 0).texture(u, v).next();
        buf.vertex(-1,  1, 0).texture(0, v).next();
        BufferRenderer.drawWithGlobalProgram(buf.end());

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();

        active = false;
        log("END+UPSCALE from=" + from);
    }

    /** If low-res is still active, end it now to avoid leaking state. */
    public static void ensureClosedIfOpen(String from) {
        if (active) {
            log("ensureClosedIfOpen from=" + from);
            endWorldAndUpscaleToMain(from + " (ensure)");
        }
    }
}
