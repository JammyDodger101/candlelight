package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class SetTrappedCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("set_trapped")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                .then(CommandManager.argument("trapped", BoolArgumentType.bool())
                        .executes(SetTrappedCommand::run))));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        String id = StringArgumentType.getString(context, "playerName");
        Boolean trapped = BoolArgumentType.getBool(context, "trapped");

        if (PlayerCandleHandler.candleOwners.contains(id.toLowerCase())) {
            PlayerCandleHandler.trappedPlayerBools.set(PlayerCandleHandler.candleOwners.indexOf(id.toLowerCase()), trapped);

            return 1;
        }
        else {
            return -1;
        }

    }
}
