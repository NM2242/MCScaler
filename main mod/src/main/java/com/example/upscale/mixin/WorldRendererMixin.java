package com.example.upscale.mixin;

import com.example.upscale.RenderScaler;
import com.example.upscale.util.Debug;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    /**
     * Begin low-res capture at the start of world rendering.
     * This ensures terrain, entities, particles, water, etc. are all inside the low-res FBO.
     */
    @Inject(method = "render", at = @At("HEAD"))
    private void mcs$beginLowResAtWorldHead(CallbackInfo ci) {
        Debug.log("MCScaler", "WorldRenderer.render HEAD → beginLowResIfActive()");
        RenderScaler.beginLowResIfActive();
    }
}
