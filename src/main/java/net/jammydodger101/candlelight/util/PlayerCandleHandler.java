package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.effect.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
The main bulk of the candlelight mod
Handles most of the logic to do with the candles
 */

public class PlayerCandleHandler
{
    // lists of variables to keep track of
    public static List<Block> candles = new ArrayList<>();
    public static List<String> candleOwners = new ArrayList<>();
    public static List<Boolean> candleStatus = new ArrayList<>();
    public static List<Boolean> trappedPlayerBools = new ArrayList<>();
    public static List<GlobalPos> candleCoordinates = new ArrayList<>();
    public static int listPos = 0;

    // adds the player specific data to the lists
    public static void addCandlesToList() {
        listAdder(ModBlocks.JAMMY_CANDLE, "jammydodger101", false);
        listAdder(ModBlocks.POM_CANDLE, "pompomdexter", false);
        listAdder(ModBlocks.SPAM_CANDLE, "citramin", false);
        //season two!!
        listAdder(ModBlocks.CRAY_CANDLE, "crayzink", false);
        listAdder(ModBlocks.EM_CANDLE, "longpotter", false);
        listAdder(ModBlocks.CROC_CANDLE, "crocksmarter", false);
        //season three!!!
        listAdder(ModBlocks.JK_CANDLE, "not_jk", false);
        listAdder(ModBlocks.MUST_CANDLE, "callmemustard_", false);
        listAdder(ModBlocks.SOAP_CANDLE, "ashpffwho", false);
        listAdder(ModBlocks.GEO_CANDLE, "georgehminer", false);
        //season four!!!!
        listAdder(ModBlocks.TEA_CANDLE, "teaish7", false);
        listAdder(ModBlocks.LEAN_CANDLE, "leantheliquid", false);
        listAdder(ModBlocks.MARS_CANDLE, "jellirolls", false);
        listAdder(ModBlocks.DELI_CANDLE, "deliwarp", false);
        listAdder(ModBlocks.SMOO_CANDLE, "smooothie", false);
    }

    // adds general variables to the list
    public static void listAdder(Block block, String playerName, Boolean candleStatusBool) {
        candles.add(block);
        candleOwners.add(playerName);
        candleStatus.add(candleStatusBool);
        trappedPlayerBools.add(null);
        candleCoordinates.add(null);
    }

    public static int getListLocation(Block candle) {
        return candles.indexOf(candle);
    }

    public static void setCandleCoordinates(BlockPos pos, BlockState state, Block block, World world) {
        if (!world.isClient()) {
            int index;
            if (state.getBlock() == block) {
                index = getListLocation(block);
                candleCoordinates.set(index,GlobalPos.create(world.getRegistryKey(), pos));
            } else if (block == null) {
                index = getListLocation(state.getBlock());
                candleCoordinates.set(index,null);
            }
        }
    }

    public static GlobalPos getCandleCoordinates(String playerName, ServerPlayerEntity player) {
        if(candleOwners.contains(playerName.toLowerCase())) {
            int index = candleOwners.indexOf(playerName.toLowerCase());
            return candleCoordinates.get(index);
        }
        return null;
    }

    public static Boolean checkPlayerStatus(PlayerEntity player) {
        String playerName = player.getName().getString();
        if (candleOwners.contains(playerName.toLowerCase())) {
            return candleStatus.get(candleOwners.indexOf(playerName.toLowerCase()));
        }
        return null;
    }

    public static Boolean checkPlayerStatusCommand(String playerName) {
        if (candleOwners.contains(playerName.toLowerCase())) {
            return candleStatus.get(candleOwners.indexOf(playerName.toLowerCase()));
        }
        return null;
    }

    public static Boolean checkPlayerTrappedStatusCommand(String playerName) {
        if (candleOwners.contains(playerName.toLowerCase())) {
            return trappedPlayerBools.get(candleOwners.indexOf(playerName.toLowerCase()));
        }
        return null;
    }

    public static void changeCandleStatus(Block candleBlock, boolean newStatus, World world) {
        try {
            listPos = candles.indexOf(candleBlock);
        } catch (Exception e) {
            return;
        }
        if (!world.isClient()) {
            candleStatus.set(listPos, newStatus);
        }
    }

    public static void changePlayerTrappedStatus(PlayerEntity player, boolean newStatus) {
        changePlayerTrappedStatus(player.getName().getString(), newStatus);
    }

    public static void changePlayerTrappedStatus(String playerName, boolean newStatus) {
        try {
            listPos = candleOwners.indexOf(playerName.toLowerCase());
        } catch (Exception e) {
            return;
        }
        if (listPos != -1) {
            trappedPlayerBools.set(listPos, newStatus);
        }
    }

    public static void applyEffectsToTrappedPlayers(World world) {
        int listPos = 0;

        for (Boolean trapped :
                trappedPlayerBools) {

            if(trapped!=null) {
                String playerName = candleOwners.get(listPos);

                ServerPlayerEntity serverPlayer = Objects.requireNonNull(world.getServer()).getPlayerManager().getPlayer(playerName);

                if (serverPlayer != null) {
                    if (trapped || serverPlayer.getCommandTags().contains("trapped")) {

                        if (world.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {
                            if (!serverPlayer.hasStatusEffect(ModEffects.EXTINGUISHED)) {
                                serverPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.EXTINGUISHED, -1 , 0, false, false, false));
                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, -1 , 0, false, false, false));

                            }
                        }
                    }
                }
            }
            listPos++;
        }
    }

    public static void reviveEveryone(ServerWorld serverWorld) {
        BlockPos worldSpawn = serverWorld.getSpawnPos();

        int listPos = 0;

        if (trappedPlayerBools.contains(true)) {

            Candlelight.LOGGER.info("Reviving Players");

            for (Boolean trapped :
                    trappedPlayerBools) {

                if (trapped != null) {
                    String playerName = candleOwners.get(listPos);

                    ServerPlayerEntity serverPlayer = serverWorld.getServer().getPlayerManager().getPlayer(playerName);

                    if (serverPlayer != null) {
                        if (trapped) {
                            serverPlayer.removeCommandTag("trapped");
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), Set.of(), 0f, 0f, true);

                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                if (serverPlayer.hasStatusEffect(ModEffects.EXTINGUISHED)) {

                                    serverPlayer.removeStatusEffect(ModEffects.EXTINGUISHED);
                                    serverPlayer.removeStatusEffect(StatusEffects.DARKNESS);
                                }
                            }
                        }
                    }
                }
                listPos++;
            }
        }
    }

    public static void reviveCommand(ServerWorld serverWorld) {
        reviveEveryone(serverWorld);
    }
}
