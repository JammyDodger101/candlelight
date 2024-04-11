package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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


        listAdder(ModBlocks.JAMMY_CANDLE, "yay", false, false);
        listAdder(ModBlocks.POM_CANDLE, "PomPomDexter", false, false);
        listAdder(ModBlocks.SPAM_CANDLE, "Spamhash", false, false);
        listAdder(ModBlocks.CRAY_CANDLE, "CrayZink", false, false);
        listAdder(ModBlocks.EM_CANDLE, "Longpotter", false, false);
        listAdder(ModBlocks.CROC_CANDLE, "CrockSmarter", false, false);
        listAdder(ModBlocks.CAT_CANDLE, "a_random_cat", false, false);
        listAdder(ModBlocks.LEAN_CANDLE, "LeanTheLiquid", false, false);
        listAdder(ModBlocks.DELUXE_CANDLE, "RealDeluxe", false, false);

        //addDataToFile("jammyCandle", MinecraftClient.getInstance().getSession().getUsername(), "JAMMY_CANDLE", false, false);
        //addDataToFile("pomCandle", "PomPomDexter", "POM_CANDLE", false, false);
    }

    public static void listAdder(Block block, String playerName, Boolean candleStatusBool, Boolean playerTrapped) {
        candles.add(block);
        candleOwners.add(playerName);
        candleStatus.add(candleStatusBool);
        trappedPlayerBools.add(null);
        //candleCoordinates.add(coordinates);
        //, List<Double> coordinates
    }

    // this version is for the start function
    private static void addDataToFile(String fileName, String playerName, String candleName, boolean lit, boolean trapped) {
        JSONObject obj = new JSONObject();

        obj.put("playerName", playerName);
        obj.put("candleName", candleName);

        // file in run folder in directory
        String fileLocation = "resources\\" + fileName + ".json";

        JSONParser parser = new JSONParser();

        //gets the file and sees if the lit and trapped variables are set
        try {
            Object object = parser.parse(new FileReader(fileLocation));
            JSONObject jsonObject = (JSONObject)object;
            Boolean isLit = (Boolean) jsonObject.get("lit");
            Boolean isTrapped = (Boolean) jsonObject.get("trapped");

            //if lit and trapped are there then don't change them
            if (isLit != null && isTrapped != null) {
                lit = isLit;
                trapped = isTrapped;
            } //if not there then we input them so that there is at least a placeholder

            try (FileWriter file = new FileWriter(fileLocation)) {
                obj.put("lit", lit);
                obj.put("trapped", trapped);
                file.write(obj.toJSONString());
                //System.out.println("JSON Object write to a File successfully");
                //System.out.println("JSON Object: " + obj);
                listAdder(stringToCandle(candleName), playerName, lit, trapped);
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        } catch(Exception e) {
            e.printStackTrace();


        }


    }

    // for updating the lit or trapped variables
    // 1 = trapped
    // 2 = lit
    private static void addDataToFile(String fileName, boolean newBool, int trappedOrLit) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        String fileLocation = "resources\\" + fileName + ".json";

        try {
            Object object = parser.parse(new FileReader(fileLocation));
            JSONObject jsonObject = (JSONObject)object;
            String playerName = (String) jsonObject.get("playerName");
            String playerCandle = (String) jsonObject.get("candleName");

            if (trappedOrLit == 1) {
                Boolean lit = (Boolean) jsonObject.get("lit");
                Boolean trapped = newBool;

                obj.put("lit", lit);
                obj.put("trapped", trapped);
            } else if (trappedOrLit == 2) {
                Boolean trapped = (Boolean) jsonObject.get("trapped");
                Boolean lit = newBool;

                obj.put("lit", lit);
                obj.put("trapped", trapped);

            }

            obj.put("playerName", playerName);
            obj.put("candleName", playerCandle);

            try (FileWriter file = new FileWriter(fileLocation)) {

                file.write(obj.toJSONString());
                //System.out.println("JSON Object write to a File successfully");
                //System.out.println("JSON Object: " + obj);
            } catch(Exception e) {
                e.printStackTrace();

            }


        } catch(Exception e) {
            e.printStackTrace();

        }

    }

    public static int getListLocation(Block candle) {
        return candles.indexOf(candle);
    }

    //ignore the shitty coding i don't know a better way to do this
    private static Block stringToCandle(String candleName) {
        if(Objects.equals(candleName, "JAMMY_CANDLE")) {
            return ModBlocks.JAMMY_CANDLE;
        } else if(Objects.equals(candleName, "POM_CANDLE")) {
            return ModBlocks.POM_CANDLE;
        }else if(Objects.equals(candleName, "CAT_CANDLE")) {
            return ModBlocks.CAT_CANDLE;
        }else if(Objects.equals(candleName, "CRAY_CANDLE")) {
            return ModBlocks.CRAY_CANDLE;
        }else if(Objects.equals(candleName, "CROC_CANDLE")) {
            return ModBlocks.CROC_CANDLE;
        }else if(Objects.equals(candleName, "EM_CANDLE")) {
            return ModBlocks.EM_CANDLE;
        }else if(Objects.equals(candleName, "LEAN_CANDLE")) {
            return ModBlocks.LEAN_CANDLE;
        }else if(Objects.equals(candleName, "SPAM_CANDLE")) {
            return ModBlocks.SPAM_CANDLE;
        }
        return null;
    }

    //ignore the shitty coding i don't know a better way to do this
    private static String candleToString(Block candle) {
        if(Objects.equals(candle, ModBlocks.JAMMY_CANDLE)) {
            return "jammyCandle";
        } else if(Objects.equals(candle, ModBlocks.POM_CANDLE)) {
            return "pomCandle";
        }else if(Objects.equals(candle, ModBlocks.CAT_CANDLE)) {
            return "catCandle";
        }else if(Objects.equals(candle, ModBlocks.CRAY_CANDLE)) {
            return "crayCandle";
        }else if(Objects.equals(candle, ModBlocks.CROC_CANDLE)) {
            return "crocCandle";
        }else if(Objects.equals(candle, ModBlocks.EM_CANDLE)) {
            return "emCandle";
        }else if(Objects.equals(candle, ModBlocks.LEAN_CANDLE)) {
            return "leanCandle";
        }else if(Objects.equals(candle, ModBlocks.SPAM_CANDLE)) {
            return "spamCandle";
        }
        return null;
    }

    public static void setCandleCoordinates(BlockPos pos, BlockState state, Block block) {
        if (state.getBlock() == block) {
            candleCoordinates.set(getListLocation(block),pos);
        } else if (block == null) {
            candleCoordinates.set(getListLocation(state.getBlock()),null);
        }
    }

    public static BlockPos getCandleCoordinates(String playerName) {
        for (Block candle : candles
        ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)).toLowerCase(), playerName.toLowerCase())) {
                return candleCoordinates.get(candles.indexOf(candle));
            }

        }
        return null;
    }


    public static Boolean checkPlayerStatus(PlayerEntity player) {

        for (Block candle : candles
             ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)), player.getName().getString())) {
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
        for (Block candle : candles
        ) {
            if (Objects.equals(candleOwners.get(getListLocation(candle)).toLowerCase(), playerName.toLowerCase())) {
                return trappedPlayerBools.get(candles.indexOf(candle));
            }
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
        try {
            listPos = candleOwners.indexOf(player.getName().getString());
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
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld, 58, 112, 200, Set.of(), 0f, 0f);
                                serverPlayer.fallDistance = 0.0f;

                                CandleData.setTrapped(((IEntityDataSaver) serverPlayer), false);
                                trappedPlayerBools.set(listPos, false);

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
                            if (serverWorld.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {

                                serverPlayer.stopRiding();
                                serverPlayer.teleport(serverWorld.getServer().getOverworld(), 58, 112, 200, Set.of(), 0f, 0f);
                                serverPlayer.fallDistance = 0.0f;

                                trappedPlayerBools.set(listPos, false);
                                CandleData.setTrapped(((IEntityDataSaver) serverPlayer), false);

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
