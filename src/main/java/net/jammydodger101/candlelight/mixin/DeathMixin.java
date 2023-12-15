package net.jammydodger101.candlelight.mixin;

import com.ibm.icu.util.Output;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.client.font.UnihexFont;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class DeathMixin {

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "respawnPlayer", at = @At("HEAD"))
    private void afterRespawn(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        oldPlayer.setSpawnPoint(ModDimension.CANDLELESS_KEY, new BlockPos(0, 0, 0), 0f, true, false);
    }

}

