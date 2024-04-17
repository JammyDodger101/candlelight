package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static java.lang.Integer.parseInt;

public class CandleLocationConverter {
    public static String BlockPosToString(BlockPos pos) {
        String posString = (pos.toShortString());
        return posString;
    }

    public static BlockPos StringToBlockPos(String string) {

        try {
            //presuming string is in the same format as to short string converter
            string = string.replaceAll("\\[","");
            string = string.replaceAll("]","");
            string = string.replaceAll(" ","");
            //Candlelight.LOGGER.info(string);
            String[] stringPositions = string.split(",");

            BlockPos pos = new BlockPos(
                    parseInt(stringPositions[0]),
                    parseInt(stringPositions[1]),
                    parseInt(stringPositions[2]));


            return pos;
        } catch(NumberFormatException e) {
            int bitLimit = 2147483646;
            return new BlockPos(bitLimit,bitLimit,bitLimit);
        }

    }
}
