package net.jammydodger101.candlelight.block;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/*
Registers all the blocks and block items for the candlelight mod
 */

public class ModBlocks {

    // setting candles
    public static final Block JAMMY_CANDLE = registerBlock("jammy_candle", new PlayerCandleBlock(AbstractBlock.Settings.create().resistance(999)));
    public static final Block POM_CANDLE = registerBlock("pom_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block SPAM_CANDLE = registerBlock("spam_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block CRAY_CANDLE = registerBlock("cray_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block EM_CANDLE = registerBlock("em_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block CROC_CANDLE = registerBlock("croc_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block JK_CANDLE = registerBlock("jk_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block MUST_CANDLE = registerBlock("must_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block SOAP_CANDLE = registerBlock("soap_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block GEO_CANDLE = registerBlock("geo_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block TEA_CANDLE = registerBlock("tea_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block SMOO_CANDLE = registerBlock("smoo_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block MARS_CANDLE = registerBlock("mars_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block LEAN_CANDLE = registerBlock("lean_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block DELI_CANDLE = registerBlock("deli_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    // setting candle cakes as they need it for the model register
    public static final Block JAMMY_CANDLE_CAKE = registerBlock("jammy_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block POM_CANDLE_CAKE = registerBlock("pom_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SPAM_CANDLE_CAKE = registerBlock("spam_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CRAY_CANDLE_CAKE = registerBlock("cray_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block EM_CANDLE_CAKE = registerBlock("em_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CROC_CANDLE_CAKE = registerBlock("croc_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block JK_CANDLE_CAKE = registerBlock("jk_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block MUST_CANDLE_CAKE = registerBlock("must_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SOAP_CANDLE_CAKE = registerBlock("soap_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block GEO_CANDLE_CAKE = registerBlock("geo_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block TEA_CANDLE_CAKE = registerBlock("tea_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SMOO_CANDLE_CAKE = registerBlock("smoo_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block MARS_CANDLE_CAKE = registerBlock("mars_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block LEAN_CANDLE_CAKE = registerBlock("lean_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block DELI_CANDLE_CAKE = registerBlock("deli_candle_cake", new CandleCakeBlock(Blocks.CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));

    // setting candleless floor block, copies the properties of bedrock
    public static final Block CANDLELESS_FLOOR_BLOCK = registerBlock("candleless_floor_block", new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK)));


    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Candlelight.MOD_ID, id), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Candlelight.MOD_ID, name), block);

    }

    // registers the candle items so that they can only be stacked to 1 and can't be burned in lava
    private static void registerBlockItem(String name, Block block) {
        if (block instanceof PlayerCandleBlock) {
            Registry.register(Registries.ITEM, Identifier.of(Candlelight.MOD_ID, name), new BlockItem(block, new Item.Settings().fireproof().maxCount(1)));
        } else {
            Registry.register(Registries.ITEM, Identifier.of(Candlelight.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        }
    }

    public static void registerModBlocks() {
        Candlelight.LOGGER.info("Registering Mod Blocks for " + Candlelight.MOD_ID);
    }
}
