package com.example.upscale.mixin;

import com.example.upscale.RenderScaler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void mcScaler$begin(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
                                net.minecraft.client.render.Camera camera, net.minecraft.client.render.GameRenderer gameRenderer,
                                net.minecraft.client.render.LightmapTextureManager lightmap, org.joml.Matrix4f projection,
                                CallbackInfo ci) {
        if (com.example.upscale.RenderScaler.isBridgeDriving()) { return; }
        RenderScaler.beginWorldLowRes("WorldRenderer#render HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void mcScaler$end(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
                              net.minecraft.client.render.Camera camera, net.minecraft.client.render.GameRenderer gameRenderer,
                              net.minecraft.client.render.LightmapTextureManager lightmap, org.joml.Matrix4f projection,
                              CallbackInfo ci) {
        if (com.example.upscale.RenderScaler.isBridgeDriving()) { return; }
        RenderScaler.endWorldAndUpscaleToMain("WorldRenderer#render TAIL");
    }
}
