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

    /**
     * End + upscale BEFORE the hand is rendered.
     * Ensures hand and HUD render natively on the main framebuffer.
     */
    @Inject(
            method = "render(FJZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Camera;F)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void mcs$endUpscaleBeforeHand(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Debug.log("MCScaler", "GameRenderer.render BEFORE renderHand → endLowResAndUpscale()");
        RenderScaler.endLowResAndUpscale();
    }
}
