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


        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CANDLELESS_FLOOR_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.REVIVER, Models.GENERATED);
        itemModelGenerator.register(ModItems.EVENT_FRAGMENT, Models.GENERATED);
        itemModelGenerator.registerCompass(ModItems.CANDLE_COMPASS);
        itemModelGenerator.register(ModItems.LIFESTEAL_HEART, Models.GENERATED);
        itemModelGenerator.register(ModItems.CROCKSMARTER_BLADE, Models.GENERATED);
    }
}
