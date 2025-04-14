package net.jammydodger101.candlelight;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.command.ModCommands;
import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.effect.ModEffects;
import net.jammydodger101.candlelight.event.AttackEntityHandler;
import net.jammydodger101.candlelight.item.ModItemGroups;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Candlelight implements ModInitializer {
	public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		// ModLootTableModifiers.modifyLootTables();

		AttackEntityCallback.EVENT.register(new AttackEntityHandler());

		ModDimension.register();

		PlayerCandleHandler.addCandlesToList();

		ModCommands.init();

		ModDataComponentTypes.registerDataComponentTypes();

		ModEffects.registerEffects();
	}
}