package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerCandleHandler {

    public static List<Block> candles = new ArrayList<>();
    public static List<String> candleOwners = new ArrayList<>();
    public static List<Boolean> candleStatus = new ArrayList<>();

    public static int listPos = 0;

    public static void addCandlesToList() {
        candles.add(ModBlocks.JAMMY_CANDLE);
        candleOwners.add(MinecraftClient.getInstance().getSession().getUsername());
        candleStatus.add(true);
    }



    public static Boolean checkPlayerStatus(PlayerEntity player) {
        player.sendMessage(Text.literal("searching"));
        //player.sendMessage(Text.literal(player.getName().getString()));


        for (Block candle : candles
             ) {
            player.sendMessage(Text.literal(player.getName().getString()));
            player.sendMessage(Text.literal(candleOwners.get(candles.indexOf(candle))));
            if (Objects.equals(candleOwners.get(candles.indexOf(candle)), player.getName().getString())) {
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



}
