package net.jammydodger101.candlelight.util;

import net.minecraft.nbt.NbtCompound;

public class CandleData {

    public static boolean setStatus(IEntityDataSaver player, boolean newStatus) {
        NbtCompound nbt = player.getPersistantData();
        //boolean status = nbt.getBoolean("status");

        nbt.putBoolean("status", newStatus);

        return newStatus;
    }

    public static void setTrapped(IEntityDataSaver player, boolean trapped) {
        NbtCompound nbt = player.getPersistantData();
        //boolean status = nbt.getBoolean("status");
        nbt.putBoolean("trapped", trapped);

    }




}
