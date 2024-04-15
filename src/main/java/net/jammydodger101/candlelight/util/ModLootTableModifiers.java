package net.jammydodger101.candlelight.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModLootTableModifiers {

    public static final Identifier ANCIENT_CITY_ID =
            new Identifier("minecraft", "chests/ancient_city");
    public static final Identifier ANCIENT_CITY_ICE_ID =
            new Identifier("minecraft", "chests/ancient_city_ice_box");

    public static final Identifier DESERT_PYRAMID = new Identifier("minecraft", "archaeology/desert_pyramid");
    public static final Identifier DESERT_WELL = new Identifier("minecraft", "archaeology/desert_well");
    public static final Identifier OCEAN_RUIN_COLD = new Identifier("minecraft", "archaeology/ocean_ruin_cold");
    public static final Identifier OCEAN_RUIN_WARM = new Identifier("minecraft", "archaeology/ocean_ruin_warm");
    public static final Identifier TRAIL_RUINS_COMMON = new Identifier("minecraft", "archaeology/trail_ruins_common");
    public static final Identifier TRAIL_RUINS_RARE = new Identifier("minecraft", "archaeology/trail_ruins_rare");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(ANCIENT_CITY_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .with(ItemEntry.builder(ModItems.EVENT_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if(ANCIENT_CITY_ICE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .with(ItemEntry.builder(ModItems.EVENT_FRAGMENT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }


        });

        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if(DESERT_PYRAMID.equals(id) || DESERT_WELL.equals(id) || OCEAN_RUIN_COLD.equals(id)|| OCEAN_RUIN_WARM.equals(id)|| TRAIL_RUINS_RARE.equals(id) || TRAIL_RUINS_COMMON.equals(id)) {
                List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));
                entries.add(ItemEntry.builder(ModItems.EVENT_FRAGMENT).build());

                LootPool.Builder pool = LootPool.builder().with(entries);
                return LootTable.builder().pool(pool).build();
            }


            return null;
        });
    }
}
