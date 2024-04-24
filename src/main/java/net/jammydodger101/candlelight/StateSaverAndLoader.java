package net.jammydodger101.candlelight;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.Supplier;


public class StateSaverAndLoader extends PersistentState {

    public Integer totalDirtBlocksBroken = 0;

    public HashMap<Integer, String> candleLocations = new HashMap<>();
    public HashMap<String, Boolean> candleStatuses = new HashMap<>();
    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound locationsTag = new NbtCompound();
        candleLocations.forEach((index, candleLocation) -> locationsTag.putInt(candleLocation, index));
        nbt.put("locations", locationsTag);

        NbtCompound statusTag = new NbtCompound();
        candleStatuses.forEach((playerName, status) -> {
            statusTag.putBoolean(playerName, status);
        });
        nbt.put("statuses", statusTag);

        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();

            playerNbt.putBoolean("trapped", playerData.trapped);

            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);

        return nbt;
    }


    public static StateSaverAndLoader createFromNBT(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();

        NbtCompound locationsCompound = tag.getCompound("locations");
        locationsCompound.getKeys().forEach(s -> {
            String location = s;
            int index = locationsCompound.getInt(s);
            state.candleLocations.put(index,location);
        });

        NbtCompound statusCompound = tag.getCompound("statuses");
        statusCompound.getKeys().forEach(playerName -> {
            Boolean status = statusCompound.getBoolean(playerName);
            state.candleStatuses.put(playerName,status);
        });


        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            playerData.trapped = playersNbt.getCompound(key).getBoolean("trapped");

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();

        //getOrCreate(Function<NbtCompound, T> readFunction, Supplier<T> supplier, String id)
        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::createFromNBT, StateSaverAndLoader::new,  Candlelight.MOD_ID);

        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(Objects.requireNonNull(player.getWorld().getServer()));

        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }


}
