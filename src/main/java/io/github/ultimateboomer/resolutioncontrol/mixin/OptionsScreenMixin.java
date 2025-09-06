package io.github.ultimateboomer.resolutioncontrol.mixin;

import io.github.ultimateboomer.resolutioncontrol.ResolutionControlMod;
import io.github.ultimateboomer.resolutioncontrol.client.gui.screen.SettingsScreen;
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
    
    protected OptionsScreenMixin(Text title) {
        super(title);
    }
    
    @Inject(method = "init", at = @At("TAIL"))
    private void addResolutionControlButton(CallbackInfo ci) {
        // Add Resolution Control button to the options screen
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = (this.width - buttonWidth) / 2;
        int y = this.height / 6 + 120; // Position it below the existing buttons
        
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("resolutioncontrol.options.button"),
                button -> {
                    ResolutionControlMod.getInstance().setLastSettingsScreen(
                            io.github.ultimateboomer.resolutioncontrol.client.gui.screen.MainSettingsScreen.class
                    );
                    this.client.setScreen(SettingsScreen.getScreen(
                            io.github.ultimateboomer.resolutioncontrol.client.gui.screen.MainSettingsScreen.class
                    ));
                }
        ).dimensions(x, y, buttonWidth, buttonHeight).build());
    }
}

