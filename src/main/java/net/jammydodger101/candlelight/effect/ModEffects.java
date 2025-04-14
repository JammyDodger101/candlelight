package net.jammydodger101.candlelight.effect;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    // adds all the attribute modifiers to the extinguished effect
    public static final RegistryEntry<StatusEffect> EXTINGUISHED = registerStatusEffect("extinguished",
            new ExtinguishedEffect(StatusEffectCategory.HARMFUL, 0xffffff)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -0.25f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -10f, EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -4f, EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -19.5f, EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,
                            Identifier.of(Candlelight.MOD_ID, "extinguished"), -1f, EntityAttributeModifier.Operation.ADD_VALUE));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Candlelight.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        Candlelight.LOGGER.info("Registering Mod Effects for " + Candlelight.MOD_ID);
    }

}
