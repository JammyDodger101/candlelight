package net.jammydodger101.candlelight.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

/*
Calculations for the candle compass
 */

public class CandleCompassFunctionality {
    public static List<GlobalPos> candleCoordinates = new ArrayList<>();
    public static List<Double> candleDistances = new ArrayList<>();
    public static BlockPos intLimitPos = new BlockPos(2147483647, 2147483647, 2147483647);

    // updates the candle coordinates list
    public static void fillCandleCoordinates(World world) {
        candleCoordinates = PlayerCandleHandler.candleCoordinates;
    }

    public static GlobalPos getNearestCandle(PlayerEntity player, World world) {
        // updates the candle coordinates list
        fillCandleCoordinates(player.getWorld());
        calculateDistancesBetweenPlayerAndCandles(player, world);

        // goes through the list of candle distances, finding the shortest one
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
            return GlobalPos.create(world.getRegistryKey(), intLimitPos);
        }

    }

    public static void calculateDistancesBetweenPlayerAndCandles(PlayerEntity playerEntity, World world) {
        // resets list
        candleDistances.clear();
        // goes through each coordinate, returning an error if the coordinate doesnt exist (candle isnt placed down) or if the candle is in another dimension
        for (GlobalPos candleCoordinate : candleCoordinates) {
            if (candleCoordinate != null) {
                if (world.getRegistryKey() == candleCoordinate.dimension()) {
                    double distance = candleCoordinate.pos().getSquaredDistance(playerEntity.getBlockPos().toCenterPos());
                    candleDistances.add(distance);
                } else {
                    candleDistances.add(2147483647.0);
                }
            } else {
                candleDistances.add(2147483647.0);
            }
        }

    }

}
