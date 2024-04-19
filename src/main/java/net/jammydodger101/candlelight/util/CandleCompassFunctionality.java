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
            //candleCoordinates = PlayerCandleHandler.candleCoordinates;
            for (int i = 0; i < PlayerCandleHandler.candleCoordinates.size(); i++) {
                candleCoordinates.add(PlayerCandleHandler.candleCoordinates.get(i));
                candleCoordinates.set(i,CandleLocationConverter.StringToBlockPos(serverState.candleLocations.get(i)));
            }

            //server.getOverworld().getRandomAlivePlayer().sendMessage(Text.literal("filled candles"));
        }


    }

    public static BlockPos getNearestCandle(PlayerEntity player) {
        if (!player.getWorld().isClient()) {
            fillCandleCoordinates(player.getWorld());
            calculateDistancesBetweenPlayerAndCandles(player);

            String playerName = player.getEntityName().toLowerCase();

            try {
                int shortestDistanceIndex = 0;
                double shortestDistance = candleDistances.get(shortestDistanceIndex);
                for (double distance : candleDistances) {
                    if (shortestDistance > distance) {

                        //if (!playerName.equals(PlayerCandleHandler.candleOwners.get(candleDistances.indexOf(distance)))) {
                            shortestDistance = distance;
                            shortestDistanceIndex = candleDistances.indexOf(distance);
                        //}

                    }
                }

                player.sendMessage(Text.literal(candleCoordinates.get(shortestDistanceIndex).toShortString()));
                player.sendMessage(Text.literal(playerName));

                return candleCoordinates.get(shortestDistanceIndex);
            } catch (IndexOutOfBoundsException e) {
                player.sendMessage(Text.literal(e.getMessage()));
                player.sendMessage(Text.literal(e.getLocalizedMessage()));
                return new BlockPos(2147483646,2147483646,2147483646);

            }
        }

        return new BlockPos(2147483646,2147483646,2147483646);

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
