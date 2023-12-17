package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jammydodger101.candlelight.block.ModBlocks;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.JAMMY_CANDLE);
        addDrop(ModBlocks.POM_CANDLE);
        addDrop(ModBlocks.SPAM_CANDLE);
        addDrop(ModBlocks.CRAY_CANDLE);
        addDrop(ModBlocks.JAMMY_CANDLE_CAKE, candleCakeDrops(ModBlocks.JAMMY_CANDLE));
        addDrop(ModBlocks.POM_CANDLE_CAKE, candleCakeDrops(ModBlocks.POM_CANDLE));
        addDrop(ModBlocks.SPAM_CANDLE_CAKE, candleCakeDrops(ModBlocks.SPAM_CANDLE));
        addDrop(ModBlocks.CRAY_CANDLE_CAKE, candleCakeDrops(ModBlocks.CRAY_CANDLE));
        addDrop(ModBlocks.RUBY_BLOCK);
    }
}
