package net.jammydodger101.candlelight;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.command.ModCommands;
import net.jammydodger101.candlelight.event.AttackEntityHandler;
import net.jammydodger101.candlelight.item.ModItemGroups;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.CandleCompassFunctionality;
import net.jammydodger101.candlelight.util.CandleLocationConverter;
import net.jammydodger101.candlelight.util.ModLootTableModifiers;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Candlelight implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "candlelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier INITIAL_SYNC = new Identifier(MOD_ID, "initial_sync");

	//private Integer totalDirtBlocksBroken = 0;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
			PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
			PacketByteBuf data = PacketByteBufs.create();

			data.writeBoolean(playerState.trapped);

			server.execute(() -> {
				ServerPlayNetworking.send(handler.getPlayer(), INITIAL_SYNC, data);

			});
		}));




		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		ModLootTableModifiers.modifyLootTables();

		AttackEntityCallback.EVENT.register(new AttackEntityHandler());

		ModDimension.register();

		PlayerCandleHandler.addCandlesToList();

		ModCommands.init();



	}
}