package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class WithdrawHeartCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("withdraw")
                        .executes(WithdrawHeartCommand::run));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        if (player.getMaxHealth() > 0f) {
            if ((player.getMaxHealth() - 2) > 0f) {
                player.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(player.getMaxHealth() - 2);
                boolean wasAdded = player.getInventory().insertStack(ModItems.LIFESTEAL_HEART.getDefaultStack());
                if (!wasAdded) {
                    player.dropItem(ModItems.LIFESTEAL_HEART.getDefaultStack(),false);
                }
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        } else {
            return -1;
        }
        return 1;
    }
}
