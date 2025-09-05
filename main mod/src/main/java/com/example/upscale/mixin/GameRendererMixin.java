package com.example.upscale.mixin;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Debug;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "renderWorld", at = @At("HEAD"), require = 0)
    private void upscaler$beginLowRes_renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        RenderScaler.beginLowResIfActive();
        Debug.log("MCScaler", "renderWorld HEAD → beginLowResIfActive()");
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;F)V",
                    shift = At.Shift.BEFORE
            ),
            require = 0
    )
    private void upscaler$presentBeforeHud(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        RenderScaler.endLowResAndUpscale();
        Debug.log("MCScaler", "render() BEFORE InGameHud.render → endLowResAndUpscale()");
    }

    @Inject(method = "render", at = @At("RETURN"), require = 0)
    private void upscaler$tickAfterFrame(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        RenderScaler.tick();
    }
}
