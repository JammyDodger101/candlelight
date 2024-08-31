package net.jammydodger101.candlelight.item;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItemComponents {
    public static final ComponentType<NbtCompound> CANDLE_COMPASS_DATA = ComponentType.<NbtCompound>builder().build();

    public static void registerItemComponents() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Candlelight.MOD_ID, "candle_pos"), CANDLE_COMPASS_DATA);
    }
}
