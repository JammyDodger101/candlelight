package net.jammydodger101.candlelight.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CANDLELIGHT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Candlelight.MOD_ID, "candlelight"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.candlelight")).icon(() -> new ItemStack(ModBlocks.JAMMY_CANDLE)).entries((displayContext, entries) -> {
        entries.add(ModBlocks.JAMMY_CANDLE);
        entries.add(ModBlocks.POM_CANDLE);
        entries.add(ModBlocks.SPAM_CANDLE);
        entries.add(ModBlocks.CRAY_CANDLE);
        entries.add(ModBlocks.EM_CANDLE);
        entries.add(ModBlocks.CROC_CANDLE);
        entries.add(ModBlocks.CAT_CANDLE);
        entries.add(ModBlocks.LEAN_CANDLE);
        //entries.add(ModBlocks.DELUXE_CANDLE);
        entries.add(ModBlocks.JK_CANDLE);
        entries.add(ModBlocks.MUST_CANDLE);
        entries.add(ModBlocks.SOAP_CANDLE);
        entries.add(ModBlocks.GEO_CANDLE);
        entries.add(ModBlocks.TONY_CANDLE);
        entries.add(ModBlocks.PRAI_CANDLE);
        entries.add(ModBlocks.GARY_CANDLE);
        entries.add(ModBlocks.MAY_CANDLE);
        entries.add(ModItems.REVIVER);
        entries.add(ModItems.EVENT_FRAGMENT);
        entries.add(ModItems.CANDLE_COMPASS);
        entries.add(ModItems.LIFESTEAL_HEART);
        //entries.add(ModItems.CROCKSMARTER_BLADE);
        entries.add(ModBlocks.CANDLELESS_FLOOR_BLOCK);
    }).build());

    public static void registerItemGroups() {
        Candlelight.LOGGER.info("Registering Item Groups for " + Candlelight.MOD_ID);
    }


}