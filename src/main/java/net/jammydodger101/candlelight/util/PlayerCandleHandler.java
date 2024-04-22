package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.PlayerData;
import net.jammydodger101.candlelight.StateSaverAndLoader;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlayerCandleHandler

{
    public static List<Block> candles = new ArrayList<>();
    public static List<String> candleOwners = new ArrayList<>();
    //public static List<PlayerEntity> trappedPlayerEntities = new ArrayList<>();
    public static List<Boolean> candleStatus = new ArrayList<>();
    public static List<Boolean> trappedPlayerBools = new ArrayList<>();
    public static List<BlockPos> candleCoordinates = new ArrayList<>();



    public static int listPos = 0;


    public static void addCandlesToList() {
        for (int i = 0; i < 20; i++) {
            //trappedPlayerEntities.add(null);

            candleCoordinates.add(null);

        }


        listAdder(ModBlocks.JAMMY_CANDLE, "jammydodger101", false, false);
        listAdder(ModBlocks.POM_CANDLE, "pompomdexter", false, false);
        listAdder(ModBlocks.SPAM_CANDLE, "citramin", false, false);
        listAdder(ModBlocks.CRAY_CANDLE, "crayzink", false, false);
        listAdder(ModBlocks.EM_CANDLE, "longpotter", false, false);
        listAdder(ModBlocks.CROC_CANDLE, "crocksmarter", false, false);
        listAdder(ModBlocks.CAT_CANDLE, "a_random_cat", false, false);
        listAdder(ModBlocks.LEAN_CANDLE, "leantheliquid", false, false);
        listAdder(ModBlocks.DELUXE_CANDLE, "realdeluxe", false, false);

    }

    public static void listAdder(Block block, String playerName, Boolean candleStatusBool, Boolean playerTrapped) {
        candles.add(block);
        candleOwners.add(playerName);
        candleStatus.add(candleStatusBool);
        trappedPlayerBools.add(null);
        //candleCoordinates.add(coordinates);
        //, List<Double> coordinates
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
        for (Block candle : candles
        ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)).toLowerCase(), playerName.toLowerCase())) {
                //return candleCoordinates.get(candles.indexOf(candle));

                Integer index = candles.indexOf(candle);
                StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(player.getServer()));

                return CandleLocationConverter.StringToBlockPos(serverState.candleLocations.get(index));
            }

        }
        return null;
    }


    public static Boolean checkPlayerStatus(PlayerEntity player) {

        for (Block candle : candles
             ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)), player.getName().getString().toLowerCase())) {
                return candleStatus.get(candles.indexOf(candle));
            }
        }
        return null;
    }



    public static Boolean checkPlayerStatusCommand(String playerName) {
        for (Block candle : candles
        ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)).toLowerCase(), playerName.toLowerCase())) {
                return candleStatus.get(candles.indexOf(candle));
            }
        }
        return null;
    }

    public static Boolean checkPlayerTrappedStatusCommand(String playerName) {
        //for (Block candle : candles
        //) {
            //if (Objects.equals(candleOwners.get(getListLocation(candle)).toLowerCase(), playerName.toLowerCase())) {
                //return trappedPlayerBools.get(candles.indexOf(candle));
            //}
        //}
        if (candleOwners.contains(playerName.toLowerCase())) {
            return trappedPlayerBools.get(candleOwners.indexOf(playerName.toLowerCase()));
        }

        return null;
    }

    public static void changeCandleStatus(Block candleBlock, boolean newStatus) {
        try {
            listPos = candles.indexOf(candleBlock);
        } catch (Exception e) {
            return;
        }
        //CandleData.setStatus()
        candleStatus.set(listPos, newStatus);
    }

    public static void changePlayerTrappedStatus(PlayerEntity player, boolean newStatus) {
        //player.sendMessage(Text.literal(player.getName().getString()));
        //player.sendMessage(Text.literal(String.valueOf((newStatus))));

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

        //CandleCompassFunctionality.fillCandleCoordinates(world);

        int listPos = 0;

        for (Boolean trapped :
                trappedPlayerBools) {

            if(trapped!=null) {
                if (trapped) {
                    String playerName = candleOwners.get(listPos);

                    ServerPlayerEntity serverPlayer = world.getServer().getPlayerManager().getPlayer(playerName);

                    if (serverPlayer != null) {
                        if (world.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {
                            if (!serverPlayer.hasStatusEffect(StatusEffects.BLINDNESS)) {

                                serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 999999999 , 2, false, false, false));
                            }
                        }
                    }

                }
            }
            listPos++;
        }
    }

    public static void reviveEveryone(PlayerEntity user, World world, Hand hand, ServerWorld serverWorld) {

        BlockPos worldSpawn = serverWorld.getSpawnPos();

        ItemStack itemStack = user.getStackInHand(hand);

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
                            PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ() , Set.of(), 0f, 0f);
                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                playerState.trapped = false;

                                if (serverPlayer.hasStatusEffect(StatusEffects.BLINDNESS)) {

                                    serverPlayer.removeStatusEffect(StatusEffects.BLINDNESS);
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
                            PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ() , Set.of(), 0f, 0f);
                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                playerState.trapped = false;

                                if (serverPlayer.hasStatusEffect(StatusEffects.BLINDNESS)) {

                                    serverPlayer.removeStatusEffect(StatusEffects.BLINDNESS);
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
