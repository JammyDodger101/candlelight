package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEffectApplierMixin {


    @Shadow public abstract ServerWorld getServerWorld();


    @Inject(method = "playerTick", at = @At("TAIL"))
    private void applyEffects(CallbackInfo ci) {
        PlayerCandleHandler.applyEffectsToTrappedPlayers(getServerWorld());

    }

}
