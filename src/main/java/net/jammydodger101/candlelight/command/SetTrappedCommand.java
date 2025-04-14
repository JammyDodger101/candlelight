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

/*
Sets a player as trapped or not
 */

public class SetTrappedCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("set_trapped")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("playerName", StringArgumentType.string())
                .then(CommandManager.argument("trapped", BoolArgumentType.bool())
                        .executes(SetTrappedCommand::run))));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // takes in the stringof the player to be modified and the new trapped status
        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        String id = StringArgumentType.getString(context, "playerName");
        boolean trapped = BoolArgumentType.getBool(context, "trapped");

        // checks to see if modified player is in the list
        if (PlayerCandleHandler.candleOwners.contains(id.toLowerCase())) {
            // sets the trapped value of the modified player
            PlayerCandleHandler.changePlayerTrappedStatus(id, trapped);

            return 1;
        }
        else {
            return -1;
        }

    }
}
