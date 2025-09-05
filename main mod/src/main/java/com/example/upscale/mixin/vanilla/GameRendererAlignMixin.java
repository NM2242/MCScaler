package com.example.upscale.mixin.vanilla;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Trace;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Precisely present *before* HUD so that the entire world + hand is captured.
 * Inject BEFORE the InGameHud.render(...) INVOKE in GameRenderer.render(FJZ)V
 * (MC 1.20.1 signature).
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererAlignMixin {

    @Inject(
        method = "render(FJZ)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
            shift = At.Shift.BEFORE
        )
    )
    private void mcs$presentBeforeHUD(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Trace.t("CompatTrace", "GameRenderer.render → BEFORE InGameHud.render (present)");
        try {
            RenderScaler.endLowResAndUpscale();
        } catch (Throwable t) {
            Trace.t("MCScaler", "endLowResAndUpscale failed");
        }
    }
}
