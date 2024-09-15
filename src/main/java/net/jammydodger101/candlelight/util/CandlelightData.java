package net.jammydodger101.candlelight.util;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CandlelightData {

    private String name;
    private int index;
    private BlockPos location;
    private boolean trapped;

    public CandlelightData(String playerName, int index, @Nullable Boolean trapped, @Nullable BlockPos location) {
        this.name = playerName;
        this.index = index;
        this.location = location;
        this.trapped = trapped;
    }

    public String getName() {
        return name;
    }

    public BlockPos getLocation() {
        return location;
    }

    public int getIndex() {
        return index;
    }

    public boolean isTrapped() {
        return trapped;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLocation(BlockPos location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrapped(boolean trapped) {
        this.trapped = trapped;
    }
}

