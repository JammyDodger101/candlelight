package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.effect.ModEffects;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
Meant to make the sky dark when the extinguished effect is active
Not used as doesn't work.
 */

@Mixin(WorldRenderer.class)
public class ExtinguishedSkyMixin {

    @Inject(method = "hasBlindnessOrDarkness", at = @At("HEAD"), cancellable = true)
    private void updatePlayer(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = camera.getFocusedEntity();
        if (entity instanceof PlayerEntity player2) {
            cir.setReturnValue(player2.hasStatusEffect(ModEffects.EXTINGUISHED));
        }
    }
}
