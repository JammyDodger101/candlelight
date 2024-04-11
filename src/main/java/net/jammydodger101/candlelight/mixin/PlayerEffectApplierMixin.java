package net.jammydodger101.candlelight.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.jammydodger101.candlelight.util.IEntityDataSaver;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerEffectApplierMixin {


    @Shadow public abstract ServerWorld getServerWorld();


    @Inject(method = "playerTick", at = @At("TAIL"))
    private void applyEffects(CallbackInfo ci) {

        PlayerCandleHandler.applyEffectsToTrappedPlayers(getServerWorld());


        //PlayerCandleHandler.changePlayerTrappedStatus(getServerWorld().play);
    }

    @Inject(method = "playerTick", at = @At("TAIL"))
    private void updateTrappedStatus(CallbackInfo ci) {
        for (ServerPlayerEntity player:
                getServerWorld().getPlayers()) {
            //PlayerCandleHandler.changePlayerTrappedStatus(player, ((IEntityDataSaver) player).getPersistantData().getBoolean("trapped"));
            //player.sendMessage(Text.literal(String.valueOf(((IEntityDataSaver) player).getPersistantData().getBoolean("trapped"))));
        }

    }

}
