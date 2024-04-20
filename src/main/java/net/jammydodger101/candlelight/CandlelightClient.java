package net.jammydodger101.candlelight;

import net.fabricmc.api.ClientModInitializer;

public class CandlelightClient implements ClientModInitializer {

    public static PlayerData playerData = new PlayerData();

    @Override
    public void onInitializeClient() {
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAMMY_CANDLE, RenderLayer.getCutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POM_CANDLE, RenderLayer.getCutout());

        //ModelPredicateProviderRegistry.register(ModItems.CANDLE_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider(((world, stack, entity) -> {
            //if (CandleCompassItem.hasCandle(stack)) {
                //return CandleCompassItem.createCandlePos(stack.getOrCreateNbt());
            //}
            //return CandleCompassItem.createSpawnPos(world);
        //})));



        //ClientPlayNetworking.registerGlobalReceiver(Candlelight.INITIAL_SYNC, ((client, handler, buf, responseSender) -> {
            //playerData.trapped = buf.readBoolean();

            //client.execute(() -> {

                //PlayerCandleHandler.changePlayerTrappedStatus(client.player, playerData.trapped);

            //});
        //}));
    }
}
