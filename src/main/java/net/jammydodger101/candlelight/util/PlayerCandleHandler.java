package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.block.custom.PlayerCandleBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.WorldSaveHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayerCandleHandler {

    public List<PlayerCandleBlock> candles = new ArrayList<>();

    public void checkCandleStatus(PlayerEntity player) {
        player.sendMessage(Text.literal("searching"));
        for (PlayerCandleBlock candle : candles
             ) {
            if (candle.PLAYER_NAME == player.getName().getString()) {
                player.sendMessage(Text.literal("detected"));
            }
        }
    }

}
