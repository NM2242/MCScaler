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
public abstract class OptionsScreensMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void mcscaler$addButton(CallbackInfo ci) {
        Screen self = (Screen)(Object)this;
        // Place above Music & Sound (x,y offsets relative to first row of buttons)
        int x = self.width / 2 - 155;
        int y = self.height / 6 - 12; // one row above first row
        self.addDrawableChild(ButtonWidget.builder(Text.literal("MCScaler"),
            b -> MinecraftClient.getInstance().setScreen(new MCScalerOptionsScreen(self)))
            .dimensions(x, y, 150, 20).build());
    }
}