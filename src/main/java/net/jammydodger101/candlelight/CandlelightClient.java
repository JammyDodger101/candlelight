package net.jammydodger101.candlelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;

public class CandlelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAMMY_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POM_CANDLE, RenderLayer.getCutout());

        //ClientPlayNetworking.registerGlobalReceiver(Candlelight.DIRT_BROKEN, (client, handler, buf, responseSender) -> {
            //int totalDirtBlockBroken = buf.readInt();
            //client.execute(() -> {
                //client.player.sendMessage(Text.literal("Total dirt blocks broken: " + totalDirtBlockBroken));
            //});
        //});
    }
}
