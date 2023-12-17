package net.jammydodger101.candlelight.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CANDLELIGHT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Candlelight.MOD_ID, "candlelight"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.candlelight")).icon(() -> new ItemStack(ModBlocks.JAMMY_CANDLE)).entries((displayContext, entries) -> {
        entries.add(ModBlocks.JAMMY_CANDLE_CAKE);
        entries.add(ModBlocks.POM_CANDLE_CAKE);
        entries.add(ModBlocks.JAMMY_CANDLE);
        entries.add(ModBlocks.POM_CANDLE);
        entries.add(ModItems.REVIVER);
    }).build());

    public static void registerItemGroups() {
        Candlelight.LOGGER.info("Registering Item Groups for " + Candlelight.MOD_ID);
    }
}