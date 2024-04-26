package net.jammydodger101.candlelight.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.custom.CandleCompassItem;
import net.jammydodger101.candlelight.item.custom.EventFragmentItem;
import net.jammydodger101.candlelight.item.custom.LifestealHeartItem;
import net.jammydodger101.candlelight.item.custom.ReviverItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    //public static final Item JAMMY_CANDLE = registerItem("jammy_candle",
            //new Item(new FabricItemSettings().maxCount(1)));

    public static final Item REVIVER = registerItem("reviver", new ReviverItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item EVENT_FRAGMENT = registerItem("event_fragments", new EventFragmentItem(new FabricItemSettings().maxCount(16).rarity(Rarity.RARE)));
    public static final Item CANDLE_COMPASS = registerItem("candle_compass", new CandleCompassItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item LIFESTEAL_HEART = registerItem("lifesteal_heart", new LifestealHeartItem(new FabricItemSettings().rarity(Rarity.EPIC)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Candlelight.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Candlelight.LOGGER.info("Registering Mod Items for " + Candlelight.MOD_ID);
    }


}
