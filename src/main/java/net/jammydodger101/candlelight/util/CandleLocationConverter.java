package net.jammydodger101.candlelight.util;

import net.minecraft.util.math.BlockPos;

import static java.lang.Integer.parseInt;

public class CandleLocationConverter {
    public static BlockPos StringToBlockPos(String string) {

        try {
            //presuming string is in the same format as to short string converter
            string = string.replaceAll("\\[","");
            string = string.replaceAll("]","");
            string = string.replaceAll(" ","");
            String[] stringPositions = string.split(",");

            BlockPos pos = new BlockPos(
                    parseInt(stringPositions[0]),
                    parseInt(stringPositions[1]),
                    parseInt(stringPositions[2]));

            return pos;
        } catch(NumberFormatException | NullPointerException e) {
            int bitLimit = 2147483646;
            return new BlockPos(bitLimit,bitLimit,bitLimit);
        }

    }
}
