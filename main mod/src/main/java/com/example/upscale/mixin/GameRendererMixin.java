package com.example.upscale.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.GameRenderer;

/**
 * GameRendererMixin no longer handles scaling directly.
 * Reserved for possible future use (shaders, bridge, etc.).
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    // intentionally left minimal
}
