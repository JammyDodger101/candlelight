package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCandle(ModBlocks.JAMMY_CANDLE, ModBlocks.JAMMY_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.POM_CANDLE, ModBlocks.POM_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.SPAM_CANDLE, ModBlocks.SPAM_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.CRAY_CANDLE, ModBlocks.CRAY_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.EM_CANDLE, ModBlocks.EM_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.CROC_CANDLE, ModBlocks.CROC_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.JK_CANDLE, ModBlocks.JK_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.MUST_CANDLE, ModBlocks.MUST_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.SOAP_CANDLE, ModBlocks.SOAP_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.GEO_CANDLE, ModBlocks.GEO_CANDLE_CAKE);
        blockStateModelGenerator.registerCandle(ModBlocks.TEA_CANDLE, ModBlocks.TEA_CANDLE_CAKE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CANDLELESS_FLOOR_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.REVIVER, Models.GENERATED);
        itemModelGenerator.register(ModItems.EVENT_FRAGMENT, Models.GENERATED);
        itemModelGenerator.registerCompass(ModItems.CANDLE_COMPASS);
        itemModelGenerator.registerCompass(ModItems.PLAYER_COMPASS);
        itemModelGenerator.register(ModItems.CROCKSMARTER_BLADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LIFESTEAL_HEART, Models.GENERATED);
    }
}
