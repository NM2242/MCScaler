package com.example.upscale;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;

public final class DebugSnapshot {
    private DebugSnapshot(){}

    public static String snapshot() {
        var mc = MinecraftClient.getInstance();
        Framebuffer fb = mc.getFramebuffer();
        int ww = mc.getWindow().getFramebufferWidth();
        int wh = mc.getWindow().getFramebufferHeight();
        String s = "[MCScaler] snapshot window=" + ww + "x" + wh +
                " fb(viewport)=" + fb.viewportWidth + "x" + fb.viewportHeight +
                " fb(texture)=" + fb.textureWidth + "x" + fb.textureHeight +
                " scale=" + UpscaleConfig.scale +
                " enabled=" + UpscaleConfig.enabled;
        System.out.println(s);
        return s;
    }
}
