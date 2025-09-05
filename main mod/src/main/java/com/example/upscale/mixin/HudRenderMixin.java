package com.example.upscale.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

@Mixin(InGameHud.class)
public class HudRenderMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void upscaler$bindDefaultFramebuffer(DrawContext context, float tickDelta, CallbackInfo ci) {
        // Ensure HUD is always drawn to the default framebuffer at full resolution
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        var w = MinecraftClient.getInstance().getWindow();
        GL11.glViewport(0, 0, w.getFramebufferWidth(), w.getFramebufferHeight());
    }
}
