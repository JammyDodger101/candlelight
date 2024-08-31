package net.jammydodger101.candlelight.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModLootTableModifiers {

    public static final RegistryKey<LootTable> ANCIENT_CITY_ID =
            LootTables.ANCIENT_CITY_CHEST;
    public static final RegistryKey<LootTable> ANCIENT_CITY_ICE_ID =
            LootTables.ANCIENT_CITY_ICE_BOX_CHEST;

    public static final RegistryKey<LootTable> DESERT_PYRAMID = LootTables.DESERT_PYRAMID_ARCHAEOLOGY;
    public static final RegistryKey<LootTable> DESERT_WELL = LootTables.DESERT_WELL_ARCHAEOLOGY;
    public static final RegistryKey<LootTable> OCEAN_RUIN_COLD = LootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY;
    public static final RegistryKey<LootTable> OCEAN_RUIN_WARM = LootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY;
    public static final RegistryKey<LootTable> TRAIL_RUINS_COMMON = LootTables.TRAIL_RUINS_COMMON_ARCHAEOLOGY;
    public static final RegistryKey<LootTable> TRAIL_RUINS_RARE = LootTables.TRAIL_RUINS_RARE_ARCHAEOLOGY;



    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(ANCIENT_CITY_ID.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .with(ItemEntry.builder(ModItems.EVENT_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if(ANCIENT_CITY_ICE_ID.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(ModItems.EVENT_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,4.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }


        });
    }
}
