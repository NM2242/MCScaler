package com.example.upscale.bridge.mixin;

import com.example.upscale.bridge.Hooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DummyTarget.class, remap = false)
public abstract class SodiumWorldRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), remap = false)
    private void mcScaler$before(Object ctx, CallbackInfo ci) {
        Hooks.beforeWorldRender();
    }

    @Inject(method = "render", at = @At("TAIL"), remap = false)
    private void mcScaler$after(Object ctx, CallbackInfo ci) {
        Hooks.afterWorldRender();
    }
}
