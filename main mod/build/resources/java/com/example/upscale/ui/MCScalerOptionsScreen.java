package com.example.upscale.ui;

import com.example.upscale.config.UpscaleConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class MCScalerOptionsScreen extends Screen {
    private final Screen parent;
    private ScaleSlider scaleSlider;

    public MCScalerOptionsScreen(Screen parent) {
        super(Text.literal("MCScaler"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;

        scaleSlider = new ScaleSlider(centerX - 100, y, 200, 20, UpscaleConfig.scale);
        addDrawableChild(scaleSlider);

        y += 28;
        addDrawableChild(CyclingButtonWidget.onOffBuilder(UpscaleConfig.sharpen)
            .build(centerX - 100, y, 200, 20, Text.literal("Sharpen (RCAS)"),
                (btn, val) -> UpscaleConfig.sharpen = val));

        y += 28;
        addDrawableChild(CyclingButtonWidget.builder(Boolean::valueOf)
            .values(java.util.Arrays.asList(Boolean.FALSE, Boolean.TRUE))
            .initially(UpscaleConfig.debug)
            .build(centerX - 100, y, 200, 20, Text.literal("Debug"),
                (btn, val) -> UpscaleConfig.debug = val));

        y += 32;
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> {
            UpscaleConfig.save();
            if (this.client != null) this.client.setScreen(parent);
        }).dimensions(centerX - 100, y, 200, 20).build());
    }

    @Override
    public void render(DrawContext dc, int mouseX, int mouseY, float delta) {
        this.renderBackground(dc);
        super.render(dc, mouseX, mouseY, delta);
        dc.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }

    private static class ScaleSlider extends SliderWidget {
        public ScaleSlider(int x, int y, int w, int h, float current) {
            super(x, y, w, h, Text.literal("Scale"), (current - 0.5f) / 0.5f);
            updateMessage();
        }
        @Override
        protected void updateMessage() {
            float v = (float)this.value;
            float s = 0.5f + v * 0.5f;
            setMessage(Text.literal("Internal Scale: " + Math.round(s * 100) + "%"));
        }
        @Override
        protected void applyValue() {
            float s = 0.5f + (float)this.value * 0.5f;
            UpscaleConfig.scale = s;
        }
    }
}