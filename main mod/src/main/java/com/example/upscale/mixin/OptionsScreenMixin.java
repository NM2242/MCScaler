package com.example.upscale.mixin;

import com.example.upscale.gui.UpscalerConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void mcscaler$addButton(CallbackInfo ci) {
        com.example.upscale.util.Debug.log("[MCScaler Main]", "OptionsScreenMixin.init width="+this.width+" height="+this.height);
        // Place near top-left like classic Options layout (left column),
        // roughly one row above Music & Sounds in vanilla.
        int x = this.width / 2 - 155;
        int y = this.height / 6 + 12;
        int _x = this.width / 2 - 155; int _y = this.height / 6 + 12; com.example.upscale.util.Debug.log("[MCScaler Main]","AddButton at x="+_x+" y="+_y);
        this.addDrawableChild(ButtonWidget.builder(Text.literal("MCScaler"), (btn) -> {
            MinecraftClient.getInstance().setScreen(new UpscalerConfigScreen((OptionsScreen)(Object)this));
        }).dimensions(x, y, 150, 20).build());
    }
}
