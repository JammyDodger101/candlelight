package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.GlobalPos;

/*
Returns the location of a specified candle
 */

public class GetCandleLocationCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("candle-location")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                        .executes(GetCandleLocationCommand::run)));
        // takes in string of player name
        // only allowed use by operators
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        String id = StringArgumentType.getString(context, "playerName");

        GlobalPos pos = PlayerCandleHandler.getCandleCoordinates(id, player);

        // returns an error if it can't find the player
        assert player != null;
        if (pos != null) {
            player.sendMessage(Text.literal("Candle is at "+ pos.toString()));
            return 1;
        }
        player.sendMessage(Text.literal("Candle is not placed down or player doesnt exist"));
        return -1;
    }
}
