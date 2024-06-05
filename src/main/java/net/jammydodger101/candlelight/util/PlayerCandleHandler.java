package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.PlayerData;
import net.jammydodger101.candlelight.StateSaverAndLoader;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlayerCandleHandler

{
    public static List<Block> candles = new ArrayList<>();
    public static List<String> candleOwners = new ArrayList<>();
    public static List<Boolean> candleStatus = new ArrayList<>();
    public static List<Boolean> trappedPlayerBools = new ArrayList<>();
    public static List<BlockPos> candleCoordinates = new ArrayList<>();
    public static int listPos = 0;


    public static void addCandlesToList() {
        listAdder(ModBlocks.JAMMY_CANDLE, "jammydodger101", false);
        listAdder(ModBlocks.POM_CANDLE, "pompomdexter", false);
        listAdder(ModBlocks.SPAM_CANDLE, "citramin", false);
        listAdder(ModBlocks.CRAY_CANDLE, "crayzink", false);
        listAdder(ModBlocks.EM_CANDLE, "longpotter", false);
        listAdder(ModBlocks.CROC_CANDLE, "crocksmarter", false);
        listAdder(ModBlocks.CAT_CANDLE, "a_random_cat", false);
        listAdder(ModBlocks.LEAN_CANDLE, "leantheliquid", false);
        listAdder(ModBlocks.DELUXE_CANDLE, "realdeluxe", false); //yikes
        listAdder(ModBlocks.JK_CANDLE, "not_jk", false);
    }

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
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
            if (state.getBlock() == block) {
                serverState.candleLocations.put(getListLocation(state.getBlock()), pos.toShortString());
                candleCoordinates.set(getListLocation(block),pos);
            } else if (block == null) {
                candleCoordinates.set(getListLocation(state.getBlock()),null);
                serverState.candleLocations.put(getListLocation(state.getBlock()), "");
            }
        }
    }

    public static BlockPos getCandleCoordinates(String playerName, ServerPlayerEntity player) {
        if(candleOwners.contains(playerName.toLowerCase())) {
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(player.getServer()));
            Integer index = candleOwners.indexOf(playerName.toLowerCase());
            return CandleLocationConverter.StringToBlockPos(serverState.candleLocations.get(index));
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

    //public static void updatePlayerTrapped(World world) {
    //    StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(world.getServer()));

        //cheeky little code slide-in
   //     for (int i = 0; i < trappedPlayerBools.size(); i++) {
   //         trappedPlayerBools.set(i,serverState.playersTrapped.get(candleOwners.get(i)));
    //    }
    //}

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
            if (world.getServer() != null) {
                StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(world.getServer()));
                serverState.candleStatuses.put(candleOwners.get(candles.indexOf(candleBlock)), newStatus);

            }
            candleStatus.set(listPos, newStatus);
        }
    }

    public static void changePlayerTrappedStatus(PlayerEntity player, boolean newStatus) {
        try {
            listPos = candleOwners.indexOf(player.getName().getString().toLowerCase());
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
                if (trapped) {
                    String playerName = candleOwners.get(listPos);

                    ServerPlayerEntity serverPlayer = world.getServer().getPlayerManager().getPlayer(playerName);

                    if (serverPlayer != null) {
                        if (world.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {
                            if (!serverPlayer.hasStatusEffect(StatusEffects.DARKNESS)) {
                                //serverPlayer.sendMessage(Text.literal(trapped.toString()));
                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 999999999 , 1, false, false, false));
                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 999999999 , 0, false, false, false));
                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 999999999 , 1, false, false, false));
                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999 , 254, false, false, false));
                                //serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 999999999 , 1, false, false, false));

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
                    if (trapped) {
                        String playerName = candleOwners.get(listPos);

                        ServerPlayerEntity serverPlayer = serverWorld.getServer().getPlayerManager().getPlayer(playerName);

                        if (serverPlayer != null) {
                            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(serverPlayer.getServer()));
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ() , Set.of(), 0f, 0f);

                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                serverState.playersTrapped.put(serverPlayer.getDisplayName().getString(), false);

                                if (serverPlayer.hasStatusEffect(StatusEffects.DARKNESS)) {

                                    serverPlayer.removeStatusEffect(StatusEffects.DARKNESS);
                                    serverPlayer.removeStatusEffect(StatusEffects.NIGHT_VISION);
                                    serverPlayer.removeStatusEffect(StatusEffects.RESISTANCE);
                                    serverPlayer.removeStatusEffect(StatusEffects.SLOWNESS);
                                    serverPlayer.removeStatusEffect(StatusEffects.SLOW_FALLING);
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
        BlockPos worldSpawn = serverWorld.getSpawnPos();

        int listPos = 0;

        if (trappedPlayerBools.contains(true)) {

            Candlelight.LOGGER.info("Reviving Players");

            for (Boolean trapped :
                    trappedPlayerBools) {

                if (trapped != null) {
                    if (trapped) {
                        String playerName = candleOwners.get(listPos);

                        ServerPlayerEntity serverPlayer = serverWorld.getServer().getPlayerManager().getPlayer(playerName);

                        if (serverPlayer != null) {
                            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(serverPlayer.getServer()));

                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ() , Set.of(), 0f, 0f);
                                serverPlayer.setSpawnPoint(World.OVERWORLD,worldSpawn,0f,true,false);
                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                serverState.playersTrapped.put(serverPlayer.getDisplayName().getString(), false);

                                if (serverPlayer.hasStatusEffect(StatusEffects.DARKNESS)) {

                                    serverPlayer.removeStatusEffect(StatusEffects.DARKNESS);
                                    serverPlayer.removeStatusEffect(StatusEffects.NIGHT_VISION);
                                    serverPlayer.removeStatusEffect(StatusEffects.RESISTANCE);
                                    serverPlayer.removeStatusEffect(StatusEffects.SLOWNESS);
                                    serverPlayer.removeStatusEffect(StatusEffects.SLOW_FALLING);
                                }
                            }
                        }
                    }
                }
                listPos++;
            }
        }
    }
}
