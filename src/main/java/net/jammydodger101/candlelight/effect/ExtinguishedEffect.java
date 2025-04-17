package net.jammydodger101.candlelight.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

/*
The extinguished effect that is applied in tandem with darkness when the player is trapped in the candleless dimension
 */

public class ExtinguishedEffect extends StatusEffect {
    public ExtinguishedEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // only applies effect if the entity is a player (sorry llamas)
    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        return entity instanceof PlayerEntity player;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
