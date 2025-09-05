package com.example.upscale.compatdebug;

import com.example.upscale.util.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Sodium 0.5.x+ renderer class:
 *   net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer
 * We use @Pseudo + targets so it compiles and runs without Sodium present.
 */
@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer", remap = false)
public abstract class SodiumWorldRendererDebugMixin {

    @Inject(method = "render", at = @At("HEAD"), require = 0)
    private void mcs$sodiumHEAD(float tickDelta, long frameStartNanos, boolean renderBlockOutline, CallbackInfo ci) {
        Debug.log("CompatTrace", "SodiumWorldRenderer.render → HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"), require = 0)
    private void mcs$sodiumTAIL(float tickDelta, long frameStartNanos, boolean renderBlockOutline, CallbackInfo ci) {
        Debug.log("CompatTrace", "SodiumWorldRenderer.render → TAIL");
    }
}
