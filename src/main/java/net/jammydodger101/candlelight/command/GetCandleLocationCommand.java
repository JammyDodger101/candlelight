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
import net.minecraft.util.math.BlockPos;

import java.util.Locale;

public class GetCandleLocationCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("candleLocation")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                //.then(CommandManager.argument("playerName", StringArgumentType.string())
                        .then(CommandManager.argument("playerName", EntityArgumentType.players())
                        .executes(GetCandleLocationCommand::run)));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayer();
        //String id = StringArgumentType.getString(context, "playerName");
        String id = EntityArgumentType.getPlayer(context, "playerName").getEntityName().toLowerCase();

        BlockPos pos = PlayerCandleHandler.getCandleCoordinates(id, player);

        if (pos != null) {

            player.sendMessage(Text.literal("Candle is at "+ pos));

            return 1;
        }

        player.sendMessage(Text.literal("Candle is not placed down or player doesnt exist"));
        return -1;
    }
}
