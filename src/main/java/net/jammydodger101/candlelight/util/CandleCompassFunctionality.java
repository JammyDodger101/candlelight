package net.jammydodger101.candlelight.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class CandleCompassFunctionality {
    public static List<BlockPos> candleCoordinates = new ArrayList<>();
    public static List<Double> candleDistances = new ArrayList<>();

    public static void fillCandleCoordinates(World world) {
        if (!world.isClient()) {
            candleCoordinates.clear();
            candleCoordinates = PlayerCandleHandler.candleCoordinates;
        }
    }

    public static BlockPos getNearestCandle(PlayerEntity player) {
        if (!player.getWorld().isClient()) {
            fillCandleCoordinates(player.getWorld());
            calculateDistancesBetweenPlayerAndCandles(player);
            //commented code is for making it not track the player's own candle
            //String playerName = player.getEntityName().toLowerCase();

            try {
                int shortestDistanceIndex = 0;
                double shortestDistance = candleDistances.get(shortestDistanceIndex);
                for (double distance : candleDistances) {
                    if (shortestDistance > distance) {
                        shortestDistance = distance;
                        shortestDistanceIndex = candleDistances.indexOf(distance);
                    }
                }
                return candleCoordinates.get(shortestDistanceIndex);
            } catch (IndexOutOfBoundsException e) {
                return new BlockPos(2147483646,2147483646,2147483646);
            }
        }
        return new BlockPos(2147483646,2147483646,2147483646);
    }

    public static void calculateDistancesBetweenPlayerAndCandles(PlayerEntity playerEntity) {
        candleDistances.clear();
        //Candlelight.LOGGER.info(candleCoordinates.toString());
        for (BlockPos candleCoordinate : candleCoordinates) {
            //Candlelight.LOGGER.info(candleCoordinate.toShortString());
            double distance = candleCoordinate.getSquaredDistance(playerEntity.getBlockPos().toCenterPos());
            candleDistances.add(distance);
        }

    }

}
