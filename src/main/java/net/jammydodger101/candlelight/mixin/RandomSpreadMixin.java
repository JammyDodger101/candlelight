package net.jammydodger101.candlelight.mixin;

import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RandomSpreadStructurePlacement.class)
public class RandomSpreadMixin {

    @Inject(method = "getSeparation", at = @At(value = "RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

    @Inject(method = "getSpacing", at = @At(value = "RETURN"), cancellable = true)
    private void injectedSpacing(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(1);
    }
}
