package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.util.LifestealHandler;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
The main bulk of the lifesteal functionality
Also deals with playing a global sound when the player gets candle banned
 */

@Mixin(ServerPlayerEntity.class)
public abstract class LifestealKillMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void lifesteal(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity deadEntity = (ServerPlayerEntity)(Object)this;
        LivingEntity attacker = deadEntity.getLastAttacker();
        // makes sure the attacker is a player
        if (attacker instanceof ServerPlayerEntity) {
            // checks to see if the player who died is on 2 health, if they are, they are then banned
            if (deadEntity.getMaxHealth() == 2) {
                BannedPlayerList bannedPlayerList = deadEntity.getServerWorld().getServer().getPlayerManager().getUserBanList();
                BannedPlayerEntry bannedPlayerEntry = new BannedPlayerEntry(deadEntity.getGameProfile(),
                        null,
                        deadEntity.getName().getString(),
                        null,
                        "You ran out of hearts!");
                bannedPlayerList.add(bannedPlayerEntry);
                deadEntity.networkHandler.disconnect(Text.translatable("multiplayer.disconnect.banned"));
                // plays a global sound to everyone if they do get lifesteal banned
                if (!deadEntity.getWorld().isClient) {
                    for (PlayerEntity player : deadEntity.getWorld().getPlayers()) {
                        player.playSoundToPlayer(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 1f, 0.5f);
                    }
                }
            }
            // increases the attackers health by 2, and decreases the dead players health by 2 (effectively transferring the heart)
            LifestealHandler.increaseHealth(attacker, 2);
            LifestealHandler.decreaseHealth(deadEntity, 2);
        }
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void playGlobalSoundIfCandlelessed(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity deadEntity = (ServerPlayerEntity)(Object)this;
        if (PlayerCandleHandler.checkPlayerStatus(deadEntity) != null) {
            if (PlayerCandleHandler.checkPlayerStatus(deadEntity) == Boolean.FALSE) {
                if (!deadEntity.getWorld().isClient) {
                    for (PlayerEntity player : deadEntity.getWorld().getPlayers()) {
                        player.playSoundToPlayer(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 1f, 0.5f);
                    }
                }
            }
        }
    }
}
