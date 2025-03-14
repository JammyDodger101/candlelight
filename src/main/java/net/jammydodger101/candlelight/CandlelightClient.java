package net.jammydodger101.candlelight;

import net.fabricmc.api.ClientModInitializer;
import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.item.custom.CandleCompassItem;
import net.jammydodger101.candlelight.item.custom.PlayerCompassItem;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class CandlelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // creates the models for the candle and player compasses

        ModelPredicateProviderRegistry.register(ModItems.CANDLE_COMPASS, Identifier.of("angle"), new CompassAnglePredicateProvider(((world, stack, entity) -> {
            if (CandleCompassItem.hasCandle(stack)) {
                return CandleCompassItem.createCandlePos(stack);
            }
            return CandleCompassItem.createSpawnPos(world);
        })));

        ModelPredicateProviderRegistry.register(ModItems.PLAYER_COMPASS, Identifier.of("angle"), new CompassAnglePredicateProvider(((world, stack, entity) -> {
            if (PlayerCompassItem.hasPlayer(stack) && PlayerCompassItem.isPlayerOnline(stack.get(ModDataComponentTypes.TARGET_PLAYER), world)) {
                return PlayerCompassItem.createPlayerPos(stack, world);
            }
            return PlayerCompassItem.createSpawnPos(world);
        })));


    }
}
