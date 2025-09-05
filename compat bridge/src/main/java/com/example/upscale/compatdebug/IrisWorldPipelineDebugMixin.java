package com.example.upscale.compatdebug;

import com.example.upscale.util.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Common Iris target:
 *   net.coderbot.iris.pipeline.WorldRenderingPipeline
 * Signatures vary; require=0 ensures no hard failure.
 */
@Pseudo
@Mixin(targets = "net.coderbot.iris.pipeline.WorldRenderingPipeline", remap = false)
public abstract class IrisWorldPipelineDebugMixin {

    @Inject(method = "render", at = @At("HEAD"), require = 0)
    private void mcs$irisHEAD(CallbackInfo ci) {
        Debug.log("CompatTrace", "Iris WorldRenderingPipeline.render → HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"), require = 0)
    private void mcs$irisTAIL(CallbackInfo ci) {
        Debug.log("CompatTrace", "Iris WorldRenderingPipeline.render → TAIL");
    }
}
