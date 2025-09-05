package com.example.upscale.mixin.vanilla;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Trace;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void mcs$hud_HEAD(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla InGameHud.render → HEAD");
        try {
            RenderScaler.endLowResAndUpscale();
        } catch (Throwable t) {
            Trace.t("MCScaler", "endLowResAndUpscale failed");
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void mcs$hud_TAIL(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Trace.t("CompatTrace", "Vanilla InGameHud.render → TAIL");
    }
}
