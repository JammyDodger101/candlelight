package net.jammydodger101.candlelight.mixin;

import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.Carver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Carver.class)
public class CarverMixin {

    @Redirect(method = "getState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/YOffset;getY(Lnet/minecraft/world/gen/HeightContext;)I"))
    private int injected(YOffset instance, HeightContext heightContext) {
        return 100;
    }
}
