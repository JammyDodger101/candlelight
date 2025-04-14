package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
Makes the player invincible if they have the extinguished effect
 */

@Mixin(LivingEntity.class)
public class ExtinguishedEffectMixin {

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"), cancellable = true)
    private void nullifyDamageIfExtinguished(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.EXTINGUISHED)) {
                cir.setReturnValue(0.0f);
            }
        }
    }

}
