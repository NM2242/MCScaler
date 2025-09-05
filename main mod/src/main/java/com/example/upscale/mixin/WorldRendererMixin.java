package com.example.upscale.mixin;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Debug;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void mcs$beginLowRes(MatrixStack matrices,
                                 float tickDelta,
                                 long limitTime,
                                 boolean renderBlockOutline,
                                 Camera camera,
                                 GameRenderer gameRenderer,
                                 LightmapTextureManager lightmapTextureManager,
                                 Matrix4f matrix4f,
                                 CallbackInfo ci) {
        Debug.log("MCScaler", "WorldRenderer.render HEAD → beginLowResIfActive()");
        RenderScaler.beginLowResIfActive();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void mcs$endAndUpscale(MatrixStack matrices,
                                   float tickDelta,
                                   long limitTime,
                                   boolean renderBlockOutline,
                                   Camera camera,
                                   GameRenderer gameRenderer,
                                   LightmapTextureManager lightmapTextureManager,
                                   Matrix4f matrix4f,
                                   CallbackInfo ci) {
        Debug.log("MCScaler", "WorldRenderer.render TAIL → endLowResAndUpscale()");
        RenderScaler.endLowResAndUpscale();
    }
}
