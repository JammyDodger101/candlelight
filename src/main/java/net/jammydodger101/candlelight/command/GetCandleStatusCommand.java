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

public class GetCandleStatusCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("candle")
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                        .executes(GetCandleStatusCommand::run)));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        String id = StringArgumentType.getString(context, "playerName");

        Boolean candleStatus = PlayerCandleHandler.checkPlayerStatusCommand(id);
        if (candleStatus != null) {
            if (candleStatus == Boolean.TRUE) {
                player.sendMessage(Text.literal("candle is lit"));
            } else {
                player.sendMessage(Text.literal("candle is NOT lit"));
            }
            return 1;
        }
        player.sendMessage(Text.literal("player does not exist"));
        return -1;
    }
}
