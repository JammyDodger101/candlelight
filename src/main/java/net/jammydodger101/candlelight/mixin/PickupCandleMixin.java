package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class PickupCandleMixin{

    @Shadow public abstract Text getName();

    @Inject(method = "onPlayerCollision", at = @At("TAIL"))
    private void applySlowness(PlayerEntity player, CallbackInfo ci) {
        if (this.getName() != null && this.getName() == ModBlocks.JAMMY_CANDLE.getName()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false, false));
        }
    }
}
