package com.example.upscale.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;

/**
 * No scaling logic here — HUD draws directly at native resolution.
 */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    // no-op
}
