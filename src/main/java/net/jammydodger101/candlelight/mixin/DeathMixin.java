package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.PlayerData;
import net.jammydodger101.candlelight.StateSaverAndLoader;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class DeathMixin {


    @Inject(method = "respawnPlayer", at = @At("HEAD"))
    private void afterRespawn(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {

        BlockPos worldSpawn = oldPlayer.getWorld().getSpawnPos();
        //if their candle status isn't null

        if(PlayerCandleHandler.checkPlayerStatus(oldPlayer) != null) {
            //if their candle is lit
            if (PlayerCandleHandler.checkPlayerStatus(oldPlayer) == Boolean.TRUE) {
                if (oldPlayer.getSpawnPointDimension() == ModDimension.CANDLELESS_KEY) {
                    oldPlayer.setSpawnPoint(World.OVERWORLD, worldSpawn, 0f, true, false);
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

