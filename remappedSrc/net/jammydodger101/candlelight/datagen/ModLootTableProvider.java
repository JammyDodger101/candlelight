package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.JAMMY_CANDLE);
        addDrop(ModBlocks.POM_CANDLE);
        addDrop(ModBlocks.SPAM_CANDLE);
        addDrop(ModBlocks.CRAY_CANDLE);
        addDrop(ModBlocks.EM_CANDLE);
        addDrop(ModBlocks.CROC_CANDLE);
        addDrop(ModBlocks.JK_CANDLE);
        addDrop(ModBlocks.MUST_CANDLE);
        addDrop(ModBlocks.SOAP_CANDLE);
        addDrop(ModBlocks.GEO_CANDLE);
        addDrop(ModBlocks.TEA_CANDLE);
        addDrop(ModBlocks.LEAN_CANDLE);
        addDrop(ModBlocks.DELI_CANDLE);
        addDrop(ModBlocks.MARS_CANDLE);
        addDrop(ModBlocks.SMOO_CANDLE);
        addDrop(ModBlocks.JAMMY_CANDLE_CAKE, candleCakeDrops(ModBlocks.JAMMY_CANDLE));
        addDrop(ModBlocks.POM_CANDLE_CAKE, candleCakeDrops(ModBlocks.POM_CANDLE));
        addDrop(ModBlocks.SPAM_CANDLE_CAKE, candleCakeDrops(ModBlocks.SPAM_CANDLE));
        addDrop(ModBlocks.CRAY_CANDLE_CAKE, candleCakeDrops(ModBlocks.CRAY_CANDLE));
        addDrop(ModBlocks.EM_CANDLE_CAKE, candleCakeDrops(ModBlocks.EM_CANDLE));
        addDrop(ModBlocks.CROC_CANDLE_CAKE, candleCakeDrops(ModBlocks.CROC_CANDLE));
        addDrop(ModBlocks.JK_CANDLE_CAKE, candleCakeDrops(ModBlocks.JK_CANDLE));
        addDrop(ModBlocks.MUST_CANDLE_CAKE, candleCakeDrops(ModBlocks.MUST_CANDLE));
        addDrop(ModBlocks.SOAP_CANDLE_CAKE, candleCakeDrops(ModBlocks.SOAP_CANDLE));
        addDrop(ModBlocks.GEO_CANDLE_CAKE, candleCakeDrops(ModBlocks.GEO_CANDLE));
        addDrop(ModBlocks.TEA_CANDLE_CAKE, candleCakeDrops(ModBlocks.TEA_CANDLE));
        addDrop(ModBlocks.LEAN_CANDLE_CAKE, candleCakeDrops(ModBlocks.LEAN_CANDLE));
        addDrop(ModBlocks.DELI_CANDLE_CAKE, candleCakeDrops(ModBlocks.DELI_CANDLE));
        addDrop(ModBlocks.MARS_CANDLE_CAKE, candleCakeDrops(ModBlocks.MARS_CANDLE));
        addDrop(ModBlocks.SMOO_CANDLE_CAKE, candleCakeDrops(ModBlocks.SMOO_CANDLE));
    }
}
