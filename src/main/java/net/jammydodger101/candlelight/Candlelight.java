package net.jammydodger101.candlelight;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.command.ModCommands;
import net.jammydodger101.candlelight.event.AttackEntityHandler;
import net.jammydodger101.candlelight.item.ModItemComponents;
import net.jammydodger101.candlelight.item.ModItemGroups;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.JsonCandlelightDataHandler;
import net.jammydodger101.candlelight.util.ModLootTableModifiers;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.UnknownCustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class Candlelight implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Integer MEMBER_NUMBER = 20;

	//private Integer totalDirtBlocksBroken = 0;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		ModLootTableModifiers.modifyLootTables();

		AttackEntityCallback.EVENT.register(new AttackEntityHandler());

		ModDimension.register();

		PlayerCandleHandler.addCandlesToList();

		ModCommands.init();

		ModItemComponents.registerItemComponents();

		JsonCandlelightDataHandler.fillLists(MEMBER_NUMBER);
		JsonCandlelightDataHandler.readInFileContent();
	}
}