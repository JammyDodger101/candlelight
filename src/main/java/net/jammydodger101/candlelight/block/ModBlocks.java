package net.jammydodger101.candlelight.block;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.block.CandleCakeBlock;

/*
Registers all the blocks and block items for the candlelight mod
 */

public class ModBlocks {

    // setting candles
    public static final Block JAMMY_CANDLE = registerCandleBlock("jammy_candle");
    public static final Block POM_CANDLE = registerCandleBlock("pom_candle");
    public static final Block SPAM_CANDLE = registerCandleBlock("spam_candle");
    public static final Block CRAY_CANDLE = registerCandleBlock("cray_candle");
    public static final Block EM_CANDLE = registerCandleBlock("em_candle");
    public static final Block CROC_CANDLE = registerCandleBlock("croc_candle");
    public static final Block JK_CANDLE = registerCandleBlock("jk_candle");
    public static final Block MUST_CANDLE = registerCandleBlock("must_candle");
    public static final Block SOAP_CANDLE = registerCandleBlock("soap_candle");
    public static final Block GEO_CANDLE = registerCandleBlock("geo_candle");
    public static final Block TEA_CANDLE = registerCandleBlock("tea_candle");
    public static final Block SMOO_CANDLE = registerCandleBlock("smoo_candle");
    public static final Block MARS_CANDLE = registerCandleBlock("mars_candle");
    public static final Block LEAN_CANDLE = registerCandleBlock("lean_candle");
    public static final Block DELI_CANDLE = registerCandleBlock("deli_candle");
    // setting candle cakes as they need it for the model register
    public static final Block JAMMY_CANDLE_CAKE = registerCandleCakeBlock("jammy_candle_cake");
    public static final Block POM_CANDLE_CAKE = registerCandleCakeBlock("pom_candle_cake");
    public static final Block SPAM_CANDLE_CAKE = registerCandleCakeBlock("spam_candle_cake");
    public static final Block CRAY_CANDLE_CAKE = registerCandleCakeBlock("cray_candle_cake");
    public static final Block EM_CANDLE_CAKE = registerCandleCakeBlock("em_candle_cake");
    public static final Block CROC_CANDLE_CAKE = registerCandleCakeBlock("croc_candle_cake");
    public static final Block JK_CANDLE_CAKE = registerCandleCakeBlock("jk_candle_cake");
    public static final Block MUST_CANDLE_CAKE = registerCandleCakeBlock("must_candle_cake");
    public static final Block SOAP_CANDLE_CAKE = registerCandleCakeBlock("soap_candle_cake");
    public static final Block GEO_CANDLE_CAKE = registerCandleCakeBlock("geo_candle_cake");
    public static final Block TEA_CANDLE_CAKE = registerCandleCakeBlock("tea_candle_cake");
    public static final Block SMOO_CANDLE_CAKE = registerCandleCakeBlock("smoo_candle_cake");
    public static final Block MARS_CANDLE_CAKE = registerCandleCakeBlock("mars_candle_cake");
    public static final Block LEAN_CANDLE_CAKE = registerCandleCakeBlock("lean_candle_cake");
    public static final Block DELI_CANDLE_CAKE = registerCandleCakeBlock("deli_candle_cake");

    // setting candleless floor block, copies the properties of bedrock
    public static final Block CANDLELESS_FLOOR_BLOCK = registerBlock("candleless_floor_block", new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK).registryKey(createKey("candleless_floor_block"))));

    public static RegistryKey<Block> createKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Candlelight.MOD_ID, id));
    }

    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Candlelight.MOD_ID, id), block);
    }

    private static Block registerBlock(String name, Block block) {
        RegistryKey<Block> key = createKey(name);
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    // registers the candle items so that they can only be stacked to 1 and can't be burned in lava
    private static void registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(Candlelight.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        if (block instanceof PlayerCandleBlock) {
            Registry.register(Registries.ITEM, key, new BlockItem(block, new Item.Settings().fireproof().maxCount(1).useBlockPrefixedTranslationKey().registryKey(key)));
        } else {
            Registry.register(Registries.ITEM, key, new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(key)));
        }
    }

    private static Block registerCandleBlock(String name) {
        RegistryKey<Block> key = createKey(name);
        Block candleBlock = new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999).registryKey(key));

        return registerBlock(name, candleBlock);
    }

    private static Block registerCandleCakeBlock(String name) {
        RegistryKey<Block> key = createKey(name);
        Block candleCakeBlock = new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE).registryKey(key));

        return registerBlock(name, candleCakeBlock);
    }

    public static void registerModBlocks() {
        Candlelight.LOGGER.info("Registering Mod Blocks for " + Candlelight.MOD_ID);
    }
}
