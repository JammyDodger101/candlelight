package net.jammydodger101.candlelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.jammydodger101.candlelight.item.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;

public class ModBlocks {

    public static final Block JAMMY_CANDLE = registerBlock("jammy_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "JammyDodger101"));
    public static final Block POM_CANDLE = registerBlock("pom_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "PomPomDexter"));
    public static final Block SPAM_CANDLE = registerBlock("spam_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "Spamhash"));
    public static final Block CRAY_CANDLE = registerBlock("cray_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "CrayZink"));
    public static final Block EM_CANDLE = registerBlock("em_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "CrayZink"));
    public static final Block CROC_CANDLE = registerBlock("croc_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "CrockSmarter"));
    public static final Block CAT_CANDLE = registerBlock("cat_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "a_random_cat"));
    public static final Block LEAN_CANDLE = registerBlock("lean_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).resistance(999), "LeanTheLiquid"));
    public static final Block DELUXE_CANDLE = registerBlock("deluxe_candle", new PlayerCandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE), "RealDeluxe"));
    public static final Block JAMMY_CANDLE_CAKE = registerBlock("jammy_candle_cake", new CandleCakeBlock(JAMMY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block POM_CANDLE_CAKE = registerBlock("pom_candle_cake", new CandleCakeBlock(POM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block SPAM_CANDLE_CAKE = registerBlock("spam_candle_cake", new CandleCakeBlock(SPAM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CRAY_CANDLE_CAKE = registerBlock("cray_candle_cake", new CandleCakeBlock(CRAY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block EM_CANDLE_CAKE = registerBlock("em_candle_cake", new CandleCakeBlock(EM_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CROC_CANDLE_CAKE = registerBlock("croc_candle_cake", new CandleCakeBlock(CROC_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block CAT_CANDLE_CAKE = registerBlock("cat_candle_cake", new CandleCakeBlock(CAT_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block LEAN_CANDLE_CAKE = registerBlock("lean_candle_cake", new CandleCakeBlock(LEAN_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    public static final Block DELUXE_CANDLE_CAKE = registerBlock("deluxe_candle_cake", new CandleCakeBlock(DELUXE_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));

    public static final Block RUBY_BLOCK = registerBlock("ruby_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block CANDLELESS_FLOOR_BLOCK = registerBlock("candleless_floor_block", new Block(FabricBlockSettings.copyOf(Blocks.BEDROCK)));


    public static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Candlelight.MOD_ID, id), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Candlelight.MOD_ID, name), block);

    }

    private static CandleBlock createCandleBlock(MapColor color) {
        return new CandleBlock(AbstractBlock.Settings.create().mapColor(color).nonOpaque().strength(0.1f).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).pistonBehavior(PistonBehavior.DESTROY));
    }

    private static CandleCakeBlock registerCandleCake(String id, Block candle) {
        return Registry.register(Registries.BLOCK, new Identifier(Candlelight.MOD_ID, id), new CandleCakeBlock(candle, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Candlelight.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Candlelight.LOGGER.info("Registering Mod Blocks for " + Candlelight.MOD_ID);
    }
}
