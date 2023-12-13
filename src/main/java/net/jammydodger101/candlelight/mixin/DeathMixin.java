package net.jammydodger101.candlelight.mixin;

import com.ibm.icu.util.Output;
import net.minecraft.client.font.UnihexFont;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class DeathMixin {
    @Shadow
    public abstract @Nullable Text getPlayerListName();

    @Inject(at = @At("RETURN"), method = "onDeath")
    private void respawnInExtinguishedDimension(DamageSource damageSource, CallbackInfo ci) {
        // This code is injected into the start of respawnPlayer method
        //if (this.getPlayerListName() ==
    }
}

