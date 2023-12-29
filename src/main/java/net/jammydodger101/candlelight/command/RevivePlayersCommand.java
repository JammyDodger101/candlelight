package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class RevivePlayersCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("revive")
                    .executes(RevivePlayersCommand::run));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerCandleHandler.reviveCommand(context.getSource().getWorld());
        return 1;
    }
}
