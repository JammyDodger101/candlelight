package net.jammydodger101.candlelight;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;


public class StateSaverAndLoader extends PersistentState {

    public Integer totalDirtBlocksBroken = 0;

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("totalDirtBlocksBroken", totalDirtBlocksBroken);

        NbtCompound playersNbt = new NbtCompound();
        players.forEach(((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();

            playerNbt.putInt("dirtBlocksBroken", playerData.dirtBlocksBroken);

            playersNbt.put(uuid.toString(), playerNbt);
        }));
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static StateSaverAndLoader createFromNBT(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.totalDirtBlocksBroken = tag.getInt("totalDirtBlocksBroken");

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            playerData.dirtBlocksBroken = playersNbt.getCompound(key).getInt("dirtBlocksBroken");

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        //getOrCreate(Function<NbtCompound, T> readFunction, Supplier<T> supplier, String id)
        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::createFromNBT, StateSaverAndLoader::new, Candlelight.MOD_ID);

        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());

        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return  playerState;
    }
}
