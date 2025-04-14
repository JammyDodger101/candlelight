package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

/*
Revives all the players trapped in candleless
 */

public class RevivePlayersCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("revive")
                    .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                    .executes(RevivePlayersCommand::run));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerCandleHandler.reviveCommand(context.getSource().getWorld());
        return 1;
    }
}
