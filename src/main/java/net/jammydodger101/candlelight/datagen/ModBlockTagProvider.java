package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.util.ModTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {


    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.CUSTOM_CANDLES)
                .add(ModBlocks.JAMMY_CANDLE)
                .add(ModBlocks.POM_CANDLE);
        getOrCreateTagBuilder(BlockTags.CANDLES)
                .add(ModBlocks.JAMMY_CANDLE)
                .add(ModBlocks.POM_CANDLE);
        getOrCreateTagBuilder(BlockTags.CANDLE_CAKES)
                .add(ModBlocks.JAMMY_CANDLE_CAKE)
                .add(ModBlocks.POM_CANDLE_CAKE);

        getOrCreateTagBuilder(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
                .add(ModBlocks.JAMMY_CANDLE)
                .add(ModBlocks.POM_CANDLE);
        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
                .add(ModBlocks.JAMMY_CANDLE)
                .add(ModBlocks.POM_CANDLE);
        getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE)
                .add(ModBlocks.JAMMY_CANDLE)
                .add(ModBlocks.POM_CANDLE);

    }
}
