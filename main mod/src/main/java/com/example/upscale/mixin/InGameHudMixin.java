package com.example.upscale.mixin;

import com.example.upscale.util.Debug;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void mcs$debugHudTrace(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Debug.log("MCScaler", "InGameHud.render TAIL → HUD drawn");
    }
}
