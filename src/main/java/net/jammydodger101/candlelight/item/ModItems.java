package net.jammydodger101.candlelight.item;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.item.custom.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

/*
Registers all the items used in the candlelight mod
 */

public class ModItems {

    public static final Item REVIVER = registerItem("reviver", new ReviverItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).registryKey(createKey("reviver"))));
    public static final Item EVENT_FRAGMENT = registerItem("event_fragments", new EventFragmentItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE).registryKey(createKey("event_fragments"))));
    public static final Item CANDLE_COMPASS = registerItem("candle_compass", new CandleCompassItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).registryKey(createKey("candle_compass"))));
    public static final Item PLAYER_COMPASS = registerItem("player_compass", new PlayerCompassItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).registryKey(createKey("player_compass"))));
    public static final Item CROCKSMARTER_BLADE = registerItem("crocksmarter_blade", new MaceItem(new Item.Settings().rarity(Rarity.EPIC).registryKey(createKey("crocksmarter_blade"))));
    public static final Item LIFESTEAL_HEART = registerItem("lifesteal_heart", new LifestealHeartItem(new Item.Settings().rarity(Rarity.EPIC).registryKey(createKey("lifesteal_heart"))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Candlelight.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Candlelight.LOGGER.info("Registering Mod Items for " + Candlelight.MOD_ID);
    }

    public static RegistryKey<Item> createKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Candlelight.MOD_ID, id));
    }

}
