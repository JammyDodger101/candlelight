package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.world.dimension.ModDimension;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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
        for (int i = 0; i < 10; i++) {
            trappedPlayerEntities.add(null);
        }
        candles.add(ModBlocks.JAMMY_CANDLE);
        candleOwners.add(MinecraftClient.getInstance().getSession().getUsername());
        candleStatus.add(false);
        trappedPlayerBools.add(false);

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

    public static void changePlayerTrappedStatus(PlayerEntity player, boolean newStatus) {
        try {
            listPos = candleOwners.indexOf(player.getName().getString());
        } catch (Exception e) {
            return;
        }
        trappedPlayerBools.set(listPos, newStatus);
        //trappedPlayerEntities.set(listPos, player);
    }

    public static void reviveEveryone(PlayerEntity user, ServerWorld destination, World world) {


        ServerWorld overWorld = ((ServerWorld)world).getServer().getWorld(World.OVERWORLD);
        ServerWorld targetWorld = ((ServerWorld)world).getServer().getWorld(ModDimension.CANDLELESS_KEY);

        //world.getServer().getPlayerManager().getPlayer()

        int listPos = 0;

        if (!trappedPlayerBools.isEmpty()) {
            for (Boolean trapped :
                    trappedPlayerBools) {

                if(trapped) {
                    String playerName = candleOwners.get(listPos);

                    ServerPlayerEntity serverPlayer = world.getServer().getPlayerManager().getPlayer(playerName);

                    serverPlayer.sendMessage(Text.literal("sending you to " + destination.toString()));
                    serverPlayer.sendMessage(Text.literal(candleStatus.get(listPos).toString()));
                    serverPlayer.sendMessage(Text.literal(candles.get(listPos).toString()));
                    serverPlayer.sendMessage(Text.literal(candleOwners.get(listPos).toString()));
                    serverPlayer.sendMessage(Text.literal(trappedPlayerBools.get(listPos).toString()));

                    serverPlayer.stopRiding();
                    serverPlayer.teleport(world.getServer().getWorld(World.OVERWORLD),0,1, 0, Set.of(), 0f, 0f);
                    serverPlayer.fallDistance = 0.0f;

                    trappedPlayerBools.set(listPos, false);
                }
                listPos++;

            }
        }



    }

}
