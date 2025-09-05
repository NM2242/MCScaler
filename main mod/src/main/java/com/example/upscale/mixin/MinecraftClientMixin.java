package com.example.upscale.mixin;

import com.example.upscale.DebugSnapshot;
import com.example.upscale.UpscaleConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private long mcscaler$last = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void mcScaler$tick(CallbackInfo ci) {
        if (UpscaleConfig.debugOverlay) {
            long now = System.currentTimeMillis();
            if (now - mcscaler$last > 2000) {
                mcscaler$last = now;
                DebugSnapshot.snapshot();
            }
        }
    }
}
