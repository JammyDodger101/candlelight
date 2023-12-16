package net.jammydodger101.candlelight.mixin;

import com.mojang.authlib.GameProfile;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEffectApplierMixin {
    @Shadow
    public abstract @Nullable Text getPlayerListName();

    private LivingEntity livingEntity;

    private int counter = 0;
    @Inject(method = "playerTick", at = @At("TAIL"))
    private void applyEffects(CallbackInfo ci) {
        counter = 0;
        for (Boolean playerTrapped:
              PlayerCandleHandler.trappedPlayer) {
            if(playerTrapped) {
                String playerName = PlayerCandleHandler.candleOwners.get(counter);
                //if(Objects.equals(playerName, )) {
                    //MinecraftClient.getInstance().world..addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 99, 1));

                //}


            }
        }
    }
}
