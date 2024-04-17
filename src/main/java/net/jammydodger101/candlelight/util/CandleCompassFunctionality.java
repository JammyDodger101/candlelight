package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.StateSaverAndLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CandleCompassFunctionality {
    public static List<BlockPos> candleCoordinates = new ArrayList<>();
    public static List<Double> candleDistances = new ArrayList<>();

    public static void fillCandleCoordinates(World world) {
        if (!world.isClient()) {
            candleCoordinates.clear();
            MinecraftServer server = world.getServer();
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(Objects.requireNonNull(server));
            for (int i = 0; i < serverState.candleLocations.size(); i++) {
                candleCoordinates.add(BlockPos.ORIGIN);
                candleCoordinates.set(i,CandleLocationConverter.StringToBlockPos(serverState.candleLocations.get(i)));
            }


            //server.getOverworld().getRandomAlivePlayer().sendMessage(Text.literal("filled candles"));
        }


    }

    public static BlockPos getNearestCandle(PlayerEntity player) {
        calculateDistancesBetweenPlayerAndCandles(player);

        try {
            int shortestDistanceIndex = 0;
            double shortestDistance = candleDistances.get(shortestDistanceIndex);
            for (double distance : candleDistances) {
                if (shortestDistance > distance) {
                    shortestDistance = distance;
                    shortestDistanceIndex = candleDistances.indexOf(distance);
                }
            }

            player.sendMessage(Text.literal(candleCoordinates.get(shortestDistanceIndex).toShortString()));

            return candleCoordinates.get(shortestDistanceIndex);
        } catch (IndexOutOfBoundsException e) {
            player.sendMessage(Text.literal("ERROR"));
            return new BlockPos(2147483646,2147483646,2147483646);
        }


    }

    public static void calculateDistancesBetweenPlayerAndCandles(PlayerEntity playerEntity) {
        candleDistances.clear();
        for (BlockPos candleCoordinate : candleCoordinates) {
            double distance = candleCoordinate.getSquaredDistance(playerEntity.getBlockPos().toCenterPos());
            candleDistances.add(distance);
            //playerEntity.sendMessage(Text.literal(String.valueOf(distance)));
            //Candlelight.LOGGER.info("Adding candle distances to array for " + Candlelight.MOD_ID);
        }

    }

}
