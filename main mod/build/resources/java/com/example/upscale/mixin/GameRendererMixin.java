package com.example.upscale.mixin;
import com.example.upscale.render.UpscaleRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    private static boolean upscaler$presentedThisFrame = false;
    @Inject(method = "render", at = @At("HEAD"))
    private void upscaler$bindLowRes(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Window w = MinecraftClient.getInstance().getWindow();
        float scale = com.example.upscale.config.UpscaleConfig.scale;
        UpscaleRenderer.ensure(w.getFramebufferWidth(), w.getFramebufferHeight(), scale);
        UpscaleRenderer.bindLowRes();
        upscaler$presentedThisFrame = false;
    }
    @Inject(method = "render", at = @At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V", shift=At.Shift.BEFORE))
    private void upscaler$presentBeforeHud(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        Window w = MinecraftClient.getInstance().getWindow();
        UpscaleRenderer.presentToScreen(w.getFramebufferWidth(), w.getFramebufferHeight());
        upscaler$presentedThisFrame = true;
    }
    @Inject(method = "render", at = @At("TAIL"))
    private void upscaler$fallbackPresent(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (!upscaler$presentedThisFrame) {
            Window w = MinecraftClient.getInstance().getWindow();
            UpscaleRenderer.presentToScreen(w.getFramebufferWidth(), w.getFramebufferHeight());
        }
        upscaler$presentedThisFrame = false;
    }
}