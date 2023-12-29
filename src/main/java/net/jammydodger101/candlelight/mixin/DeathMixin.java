package net.jammydodger101.candlelight.mixin;

import com.ibm.icu.util.Output;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.client.font.UnihexFont;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerManager.class)
public abstract class DeathMixin {


    @Inject(method = "respawnPlayer", at = @At("HEAD"))
    private void afterRespawn(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {

            if(PlayerCandleHandler.checkPlayerStatus(oldPlayer) != null) {

                if (PlayerCandleHandler.checkPlayerStatus(oldPlayer) == Boolean.TRUE) {
                    if (oldPlayer.getSpawnPointDimension() == ModDimension.CANDLELESS_KEY) {
                        oldPlayer.setSpawnPoint(World.OVERWORLD, new BlockPos(0, 0, 0), 0f, true, false);
                        PlayerCandleHandler.changePlayerTrappedStatus(oldPlayer, false);
                    }
                    PlayerCandleHandler.changePlayerTrappedStatus(oldPlayer, false);
                } else {
                    oldPlayer.setSpawnPoint(ModDimension.CANDLELESS_KEY, new BlockPos(0, 5, 0), 0f, true, false);
                    PlayerCandleHandler.changePlayerTrappedStatus(oldPlayer, true);
                }

                //oldPlayer.sendMessage(Text.literal("you should be being respawned in the other dim buddy"));
                //oldPlayer.sendMessage(Text.literal("check player status returned: " + PlayerCandleHandler.checkPlayerStatus(oldPlayer)));


            }

    }
}

