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

/*
Returns whether a player is trapped in candleless or not.
 */

public class GetPlayerTrappedStatusCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("trapped")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                        .executes(GetPlayerTrappedStatusCommand::run)));
        // takes in string of player name
        // only allowed use by operators
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // gets player name and makes sure it isnt null
        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        String id = StringArgumentType.getString(context, "playerName");

        // checks if player is trapped using player candle handler function
        Boolean trappedStatus = PlayerCandleHandler.checkPlayerTrappedStatusCommand(id);
        if (trappedStatus != null) {
            if (trappedStatus == Boolean.TRUE) {
                player.sendMessage(Text.literal("Player should be trapped in candleless"));
            } else {
                player.sendMessage(Text.literal("Player should NOT be trapped in candleless"));
            }
            return 1;
        }
        player.sendMessage(Text.literal("Player does not exist"));
        return -1;
    }
}
