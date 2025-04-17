package net.jammydodger101.candlelight.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jammydodger101.candlelight.effect.ModEffects;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
Sets the fog when the player has the extinguished effect
Not technically needed as the darkness effect basically overrides this
 */

@Mixin(BackgroundRenderer.class)
public class ExtinguishedFogMixin {

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void applyFogWhenExtinguished(Camera camera, BackgroundRenderer.FogType fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickProgress, CallbackInfoReturnable<Fog> cir) {
        Entity entity = camera.getFocusedEntity();
        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.EXTINGUISHED)) {
                RenderSystem.setShaderFog(new Fog(-8.0f, 1.0f, FogShape.SPHERE, 0f, 0f, 0f, 1f));
            }
        }
    }
}
