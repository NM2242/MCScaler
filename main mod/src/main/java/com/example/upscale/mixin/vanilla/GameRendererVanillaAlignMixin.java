package com.example.upscale.mixin.vanilla;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Trace;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererVanillaAlignMixin {

    @Inject(method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V", at = @At("HEAD"))
    private void mcs$vanillaWorld_HEAD(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla GameRenderer.renderWorld → HEAD");
        try { RenderScaler.beginLowResIfActive(); } catch (Throwable t) { Trace.t("MCScaler", "beginLowResIfActive failed"); }
    }

    @Inject(method = "render(FJZ)V", at = @At("HEAD"))
    private void mcs$gameRender_HEAD(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla GameRenderer.render → HEAD");
    }

    @Inject(method = "render(FJZ)V", at = @At("TAIL"))
    private void mcs$gameRender_TAIL(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla GameRenderer.render → TAIL");
        try { RenderScaler.tick(); } catch (Throwable t) { Trace.t("MCScaler", "tick() failed"); }
    }
}
