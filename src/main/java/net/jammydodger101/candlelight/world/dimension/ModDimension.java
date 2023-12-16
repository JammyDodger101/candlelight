package net.jammydodger101.candlelight.world.dimension;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ModDimension {

    public static final RegistryKey<World> CANDLELESS_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(Candlelight.MOD_ID, "candleless"));
    public static final RegistryKey<DimensionType> CANDLELESS_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, CANDLELESS_KEY.getValue());

    public static void register() {
        Candlelight.LOGGER.debug("Registering ModDimensions for " + Candlelight.MOD_ID);
    }
}
