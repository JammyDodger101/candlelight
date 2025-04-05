package net.jammydodger101.candlelight.mixin;

import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.placement.ConcentricRingsStructurePlacement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConcentricRingsStructurePlacement.class)
public class ConcentricStructuresMixin {

    @Inject(method = "getDistance", at = @At(value = "RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
