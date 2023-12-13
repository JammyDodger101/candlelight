package net.jammydodger101.candlelight.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jammydodger101.candlelight.Candlelight;
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

    public static final Block JAMMY_CANDLE = registerBlock("jammy_candle", new CandleBlock(AbstractBlock.Settings.copy(Blocks.CANDLE)));
    public static final Block JAMMY_CANDLE_CAKE = registerBlock("jammy_candle_cake", new CandleCakeBlock(JAMMY_CANDLE, AbstractBlock.Settings.copy(Blocks.CANDLE_CAKE)));

    public static final Block RUBY_BLOCK = registerBlock("ruby_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));


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
        return Registry.register(Registries.ITEM, new Identifier(Candlelight.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Candlelight.LOGGER.info("Registering Mod Blocks for " + Candlelight.MOD_ID);
    }
}
