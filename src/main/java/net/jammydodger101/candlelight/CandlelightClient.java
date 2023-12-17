package net.jammydodger101.candlelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;

public class CandlelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAMMY_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POM_CANDLE, RenderLayer.getCutout());
    }
}
