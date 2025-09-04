package com.example.upscale.ui;

import com.example.upscale.UpscaleConfig;
import com.example.upscale.gui.UpscalerConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MCScalerOptionsScreen extends Screen {
    private final Screen parent;

    public MCScalerOptionsScreen(Screen parent) {
        super(Text.literal("MCScaler"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        int y = this.height / 6;

        // Open config screen
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Open MCScaler Settings"), b -> {
            MinecraftClient.getInstance().setScreen(new UpscalerConfigScreen(this));
        }).dimensions(centerX, y, 200, 20).build());

        y += 24;

        // Done button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> {
            MinecraftClient.getInstance().setScreen(parent);
        }).dimensions(centerX, y, 200, 20).build());
    }
}
