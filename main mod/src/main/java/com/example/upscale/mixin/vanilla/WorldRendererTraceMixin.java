package com.example.upscale.mixin.vanilla;

import com.example.upscale.util.Trace;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Trace vanilla world pass only.
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererTraceMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void mcs$world_HEAD(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
                                Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmap,
                                Matrix4f projectionMatrix, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla WorldRenderer.render → HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void mcs$world_TAIL(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
                                Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmap,
                                Matrix4f projectionMatrix, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla WorldRenderer.render → TAIL");
    }
}
