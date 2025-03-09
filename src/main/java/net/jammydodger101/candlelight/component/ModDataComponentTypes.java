package net.jammydodger101.candlelight.component;

import com.mojang.serialization.Codec;
import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<GlobalPos> COORDINATES = register("coordinates", globalPosBuilder -> globalPosBuilder.codec(GlobalPos.CODEC));
    public static final ComponentType<String> TARGET_PLAYER = register("target_player", stringBuilder -> stringBuilder.codec(Codec.STRING));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Candlelight.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
        Candlelight.LOGGER.info("registering data component types for " + Candlelight.MOD_ID);
    }
}
