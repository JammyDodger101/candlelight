package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class CandleCompassFunctionality {
    public static List<GlobalPos> candleCoordinates = new ArrayList<>();
    public static List<Double> candleDistances = new ArrayList<>();
    public static BlockPos intLimitPos = new BlockPos(2147483647, 2147483647, 2147483647);

    public static void fillCandleCoordinates(World world) {
        candleCoordinates = PlayerCandleHandler.candleCoordinates;

    }

    public static GlobalPos getNearestCandle(PlayerEntity player, World world) {
        fillCandleCoordinates(player.getWorld());
        calculateDistancesBetweenPlayerAndCandles(player, world);
        //commented code is for making it not track the player's own candle
        //String playerName = player.getEntityName().toLowerCase();
        Candlelight.LOGGER.info(candleCoordinates.toString());
        Candlelight.LOGGER.info(candleDistances.toString());

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
        candleDistances.clear();
        //Candlelight.LOGGER.info(candleCoordinates.toString());
        for (GlobalPos candleCoordinate : candleCoordinates) {
            //Candlelight.LOGGER.info(candleCoordinate.toShortString());
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
