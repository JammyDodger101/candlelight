package net.jammydodger101.candlelight.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.block.CandleCakeBlock;

public class ModBlocks {

    public static final Block JAMMY_CANDLE = registerBlock("jammy_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block POM_CANDLE = registerBlock("pom_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block SPAM_CANDLE = registerBlock("spam_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block CRAY_CANDLE = registerBlock("cray_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block EM_CANDLE = registerBlock("em_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block CROC_CANDLE = registerBlock("croc_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block CAT_CANDLE = registerBlock("cat_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block LEAN_CANDLE = registerBlock("lean_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block DELUXE_CANDLE = registerBlock("deluxe_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE)));
    public static final Block JK_CANDLE = registerBlock("jk_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block MUST_CANDLE = registerBlock("must_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block SOAP_CANDLE = registerBlock("soap_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block GEO_CANDLE = registerBlock("geo_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block TONY_CANDLE = registerBlock("tony_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block PRAI_CANDLE = registerBlock("prai_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block GARY_CANDLE = registerBlock("gary_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block MAY_CANDLE = registerBlock("may_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999)));
    public static final Block JAMMY_CANDLE_CAKE = registerBlock("jammy_candle_cake", new CandleCakeBlock(JAMMY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block POM_CANDLE_CAKE = registerBlock("pom_candle_cake", new CandleCakeBlock(POM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SPAM_CANDLE_CAKE = registerBlock("spam_candle_cake", new CandleCakeBlock(SPAM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CRAY_CANDLE_CAKE = registerBlock("cray_candle_cake", new CandleCakeBlock(CRAY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block EM_CANDLE_CAKE = registerBlock("em_candle_cake", new CandleCakeBlock(EM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CROC_CANDLE_CAKE = registerBlock("croc_candle_cake", new CandleCakeBlock(CROC_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CAT_CANDLE_CAKE = registerBlock("cat_candle_cake", new CandleCakeBlock(CAT_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block LEAN_CANDLE_CAKE = registerBlock("lean_candle_cake", new CandleCakeBlock(LEAN_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block DELUXE_CANDLE_CAKE = registerBlock("deluxe_candle_cake", new CandleCakeBlock(DELUXE_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block JK_CANDLE_CAKE = registerBlock("jk_candle_cake", new CandleCakeBlock(JK_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block MUST_CANDLE_CAKE = registerBlock("must_candle_cake", new CandleCakeBlock(MUST_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SOAP_CANDLE_CAKE = registerBlock("soap_candle_cake", new CandleCakeBlock(SOAP_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block GEO_CANDLE_CAKE = registerBlock("geo_candle_cake", new CandleCakeBlock(GEO_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block TONY_CANDLE_CAKE = registerBlock("tony_candle_cake", new CandleCakeBlock(TONY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block PRAI_CANDLE_CAKE = registerBlock("prai_candle_cake", new CandleCakeBlock(PRAI_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block GARY_CANDLE_CAKE = registerBlock("gary_candle_cake", new CandleCakeBlock(GARY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block MAY_CANDLE_CAKE = registerBlock("may_candle_cake", new CandleCakeBlock(MAY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));

    public static final Block RUBY_BLOCK = registerBlock("ruby_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block CANDLELESS_FLOOR_BLOCK = registerBlock("candleless_floor_block", new Block(FabricBlockSettings.copyOf(Blocks.BEDROCK)));


    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Candlelight.MOD_ID, id), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Candlelight.MOD_ID, name), block);

    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Candlelight.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Candlelight.LOGGER.info("Registering Mod Blocks for " + Candlelight.MOD_ID);
    }
}
