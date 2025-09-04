package com.example.upscale.gui;

import com.example.upscale.UpscaleConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class UpscalerConfigScreen extends Screen {
    private final Screen parent;

    public UpscalerConfigScreen(Screen parent) {
        super(Text.literal("MCScaler Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        int y = this.height / 6;

        // === Scale slider ===
        this.addDrawableChild(new SliderWidget(centerX, y, 200, 20,
                Text.literal("Scale: " + String.format("%.2f", UpscaleConfig.scale)),
                UpscaleConfig.scale) {

            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Scale: " + String.format("%.2f", this.value)));
            }

            @Override
            protected void applyValue() {
                // Clamp between 0.5 and 1.0
                double clamped = Math.max(0.5, Math.min(1.0, this.value));
                UpscaleConfig.setScale(clamped);
            }
        });

        y += 24;

        // === Debug toggle ===
        this.addDrawableChild(
                CyclingButtonWidget.onOffBuilder()
                        .initially(UpscaleConfig.debugOverlay)
                        .build(centerX, y, 200, 20,
                                Text.literal("Debug Overlay"),
                                (btn, val) -> UpscaleConfig.setDebug(val))
        );

        y += 24;

        // === Done button ===
        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (b) -> {
                    this.client.setScreen(this.parent);
                }).dimensions(centerX, y, 200, 20).build()
        );
    }
}
