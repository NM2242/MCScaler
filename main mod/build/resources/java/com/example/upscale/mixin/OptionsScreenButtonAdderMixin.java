package com.example.upscale.mixin;

import com.example.upscale.ui.MCScalerOptionsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenButtonAdderMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void mcscaler$addButton(CallbackInfo ci) {
        Screen self = (Screen)(Object)this;
        int x = self.width / 2 - 155;
        int y = self.height / 6 - 12; // just above Music & Sounds
        ButtonWidget button = ButtonWidget.builder(Text.literal("MCScaler"),
            b -> MinecraftClient.getInstance().setScreen(new MCScalerOptionsScreen((OptionsScreen)(Object)this)))
            .dimensions(x, y, 150, 20).build();
        ((Screen)(Object)this).addDrawableChild(button);
        if (com.example.upscale.config.UpscaleConfig.debug) System.out.println("[MCScaler] Button injected");
    }
}