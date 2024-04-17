package net.jammydodger101.candlelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.item.custom.CandleCompassItem;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CandlelightClient implements ClientModInitializer {

    public static PlayerData playerData = new PlayerData();

    @Override
    public void onInitializeClient() {
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAMMY_CANDLE, RenderLayer.getCutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POM_CANDLE, RenderLayer.getCutout());

        ModelPredicateProviderRegistry.register(ModItems.CANDLE_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider(((world, stack, entity) -> {
            if (CandleCompassItem.hasCandle(stack)) {
                return CandleCompassItem.createCandlePos(stack.getOrCreateNbt());
            }
            return CandleCompassItem.createSpawnPos(world);
        })));

        ClientPlayNetworking.registerGlobalReceiver(Candlelight.DIRT_BROKEN, (client, handler, buf, responseSender) -> {
            int totalDirtBlockBroken = buf.readInt();
            playerData.dirtBlocksBroken = buf.readInt();

            client.execute(() -> {
                client.player.sendMessage(Text.literal("Total dirt blocks broken: " + totalDirtBlockBroken));
                client.player.sendMessage(Text.literal("Player specific dirt blocks broken: " + playerData.dirtBlocksBroken));
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(Candlelight.INITIAL_SYNC, ((client, handler, buf, responseSender) -> {
            playerData.dirtBlocksBroken = buf.readInt();
            playerData.trapped = buf.readBoolean();
            //playerData.trapped = true;


            //PlayerCandleHandler.applyEffectsToTrappedPlayers(client.world);

            client.execute(() -> {
                client.player.sendMessage(Text.literal(String.valueOf(playerData.trapped)));
                client.player.sendMessage(Text.literal("Initial specific dirt blocks broken: " + playerData.dirtBlocksBroken));
                PlayerCandleHandler.changePlayerTrappedStatus(client.player, playerData.trapped);

                //.player.getWorld().getServer()



            });
        }));
    }
}
