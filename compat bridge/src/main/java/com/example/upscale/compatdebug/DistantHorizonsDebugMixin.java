package com.example.upscale.compatdebug;

import com.example.upscale.util.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * DH internals vary across versions. We try multiple candidates.
 */
@Pseudo
@Mixin(targets = {
        "org.distant.horizons.core.render.RenderPipeline",
        "org.distanthorizons.core.render.RenderPipeline",
        "com.jozufozu.distantworlds.client.render.RenderPipeline"
}, remap = false)
public abstract class DistantHorizonsDebugMixin {

    @Inject(method = "render", at = @At("HEAD"), require = 0)
    private void mcs$dhHEAD(CallbackInfo ci) {
        Debug.log("CompatTrace", "DistantHorizons RenderPipeline.render → HEAD");
    }

    @Inject(method = "render", at = @At("TAIL"), require = 0)
    private void mcs$dhTAIL(CallbackInfo ci) {
        Debug.log("CompatTrace", "DistantHorizons RenderPipeline.render → TAIL");
    }
}
