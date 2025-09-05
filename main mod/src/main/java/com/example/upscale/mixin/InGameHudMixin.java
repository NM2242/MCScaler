package com.example.upscale.mixin;

import com.example.upscale.util.Debug;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void mcScaler$overlay(DrawContext ctx, float tickDelta, CallbackInfo ci){
        if(!Debug.overlay()) return;
        try{
            ctx.drawText(MinecraftClient.getInstance().textRenderer, Text.of(Debug.overlayLine()), 2, 2, 0xFFFFFFFF, false);
        }catch(Throwable ignored){}
    }
}
