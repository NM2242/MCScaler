package com.example.upscale.compatdebug;

import com.example.upscale.util.Debug;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class VanillaWorldRendererDebugMixin {

    @Inject(method = "render", at = @At("HEAD"), require = 0)
    private void mcs$vanillaRenderHEAD(MatrixStack matrices,
                                       float tickDelta,
                                       long limitTime,
                                       boolean renderBlockOutline,
                                       Camera camera,
                                       GameRenderer gameRenderer,
                                       LightmapTextureManager lightmap,
                                       Matrix4f projectionMatrix,
                                       CallbackInfo ci) {
        Debug.log("CompatTrace", "Vanilla WorldRenderer.render → HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"), require = 0)
    private void mcs$vanillaRenderTAIL(MatrixStack matrices,
                                       float tickDelta,
                                       long limitTime,
                                       boolean renderBlockOutline,
                                       Camera camera,
                                       GameRenderer gameRenderer,
                                       LightmapTextureManager lightmap,
                                       Matrix4f projectionMatrix,
                                       CallbackInfo ci) {
        Debug.log("CompatTrace", "Vanilla WorldRenderer.render → TAIL");
    }
}
