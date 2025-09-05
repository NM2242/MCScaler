package com.example.upscale.mixin;
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
    private void upscaler$ensureDefaultFB(net.minecraft.client.util.math.MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        var w = MinecraftClient.getInstance().getWindow();
        GL11.glViewport(0, 0, w.getFramebufferWidth(), w.getFramebufferHeight());
        // Debug overlay: small green quad to mark HUD phase
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.25f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-0.8f, 1f); GL11.glVertex2f(-0.6f, 1f);
        GL11.glVertex2f(-0.6f, 0.8f); GL11.glVertex2f(-0.8f, 0.8f);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}