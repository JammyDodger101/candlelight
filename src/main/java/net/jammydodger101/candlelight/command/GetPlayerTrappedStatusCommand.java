package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Locale;

public class GetPlayerTrappedStatusCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("trapped")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                //.then(CommandManager.argument("playerName", StringArgumentType.string())
                        .then(CommandManager.argument("playerName", EntityArgumentType.players())
                        .executes(GetPlayerTrappedStatusCommand::run)));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayer();
        String id = EntityArgumentType.getPlayer(context, "playerName").getEntityName().toLowerCase();


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
