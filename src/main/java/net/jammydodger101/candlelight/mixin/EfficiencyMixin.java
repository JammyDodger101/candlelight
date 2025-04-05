package net.jammydodger101.candlelight.mixin;

import net.minecraft.enchantment.EfficiencyEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EfficiencyEnchantment.class)
public class EfficiencyMixin {
    @Inject(method = "getMaxLevel", at = @At(value = "RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(10);
    }
}
