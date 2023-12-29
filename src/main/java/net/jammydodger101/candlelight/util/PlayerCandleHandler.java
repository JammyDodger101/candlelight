package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlayerCandleHandler {

    public static List<Block> candles = new ArrayList<>();
    public static List<String> candleOwners = new ArrayList<>();
    public static List<PlayerEntity> trappedPlayerEntities = new ArrayList<>();
    public static List<Boolean> candleStatus = new ArrayList<>();
    public static List<Boolean> trappedPlayerBools = new ArrayList<>();

    public static int listPos = 0;

    public static void addCandlesToList() {
        for (int i = 0; i < 20; i++) {
            trappedPlayerEntities.add(null);
            trappedPlayerBools.add(null);
        }

        listAdder(ModBlocks.JAMMY_CANDLE, "Jammydodger101", false, false);
        listAdder(ModBlocks.POM_CANDLE, "PomPomDexter", false, false);
        listAdder(ModBlocks.SPAM_CANDLE, "Spamhash", false, false);
        listAdder(ModBlocks.CRAY_CANDLE, "CrayZink", false, false);
        listAdder(ModBlocks.EM_CANDLE, "Longpotter", false, false);
        listAdder(ModBlocks.CROC_CANDLE, "CrockSmarter", false, false);
        listAdder(ModBlocks.CAT_CANDLE, "a_random_cat", false, false);
        listAdder(ModBlocks.LEAN_CANDLE, "LeanTheLiquid", false, false);
        listAdder(ModBlocks.DELUXE_CANDLE, "RealDeluxe", false, false);
    }

    public static void listAdder(Block block, String playerName, Boolean candleStatusBool, Boolean playerTrapped) {
        candles.add(block);
        candleOwners.add(playerName);
        candleStatus.add(candleStatusBool);
        trappedPlayerBools.add(playerTrapped);
    }



    public static Boolean checkPlayerStatus(PlayerEntity player) {
        //player.sendMessage(Text.literal("searching"));
        //player.sendMessage(Text.literal(player.getName().getString()));


        for (Block candle : candles
             ) {
            //player.sendMessage(Text.literal(player.getName().getString()));
            //player.sendMessage(Text.literal(candleOwners.get(candles.indexOf(candle))));
            if (Objects.equals(candleOwners.get(candles.indexOf(candle)), player.getName().getString())) {
                return candleStatus.get(candles.indexOf(candle));
            }
        }
        return null;
    }

    public static Boolean checkPlayerStatusCommand(String playerName) {
        //player.sendMessage(Text.literal("searching"));
        //player.sendMessage(Text.literal(player.getName().getString()));


        for (Block candle : candles
        ) {
            //player.sendMessage(Text.literal(player.getName().getString()));
            //player.sendMessage(Text.literal(candleOwners.get(candles.indexOf(candle))));
            if (Objects.equals(candleOwners.get(candles.indexOf(candle)), playerName)) {
                return candleStatus.get(candles.indexOf(candle));
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
            //player.sendMessage(Text.literal("changed player trapped status to " + newStatus + "at list pos " + listPos));
        }

        //trappedPlayerEntities.set(listPos, player);
    }

    public static void applyEffectsToTrappedPlayers(World world) {

        int listPos = 0;

        for (Boolean trapped :
                trappedPlayerBools) {

            if(trapped!=null) {
                if (trapped) {
                    String playerName = candleOwners.get(listPos);

                    ServerPlayerEntity serverPlayer = world.getServer().getPlayerManager().getPlayer(playerName);

                    //serverPlayer.sendMessage(Text.literal("ur being effected"));

                    if (serverPlayer != null) {
                        if (world.getServer().getPlayerManager().getPlayerList().contains(serverPlayer)) {
                            if (!serverPlayer.hasStatusEffect(StatusEffects.BLINDNESS)) {
                                //serverPlayer.sendMessage(Text.literal("ur being effected"));

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

            //user.sendMessage(Text.literal(itemStack.getItem().getName().toString()));
            //user.sendMessage(Text.literal(hand.name()));
            //user.sendMessage(user.getStackInHand(hand).getName());


            //user.sendMessage(Text.literal("decrement ><"));


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

                                trappedPlayerBools.set(listPos, false);

                                if (serverPlayer.hasStatusEffect(StatusEffects.BLINDNESS)) {

                                    serverPlayer.removeStatusEffect(StatusEffects.BLINDNESS);
                                }


                            }
                        }
                    }
                    //user.sendMessage(Text.literal("womp"));

                }
                listPos++;

            }
            //user.sendMessage(Text.literal(itemStack.getItem().getName().toString()));
            //user.sendMessage(Text.literal(hand.name()));
            //user.sendMessage(user.getStackInHand(hand).getName());
        }



    }

}
