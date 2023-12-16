package net.jammydodger101.candlelight.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.custom.ReviverItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFrameItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    //public static final Item JAMMY_CANDLE = registerItem("jammy_candle",
            //new Item(new FabricItemSettings().maxCount(1)));

    public static final Item REVIVER = registerItem("reviver", new ReviverItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Candlelight.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Candlelight.LOGGER.info("Registering Mod Items for " + Candlelight.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((ModItems::addItemsToIngredientTabItemGroup));
    }

}
