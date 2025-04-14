package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

/*
Withdraws a physical heart from the player
 */

public class WithdrawHeartCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("withdraw")
                        .executes(WithdrawHeartCommand::run));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // gets the player and checks it isn't null (i.e. the command isn't being run in a command block)
        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        // makes sure the player isn't on 0 health and that removing 2 health won't put them on 0 or below
        if (player.getMaxHealth() > 0f) {
            if ((player.getMaxHealth() - 2) > 0f) {
                // modifies the max health of the player to remove 2 health
                player.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(player.getMaxHealth() - 2);
                // tries to give the player the heart that they withdrew
                boolean wasAdded = player.getInventory().insertStack(ModItems.LIFESTEAL_HEART.getDefaultStack());
                if (!wasAdded) {
                    // if unable to give the player the item (ie inventory is full), it will drop the heart on the ground
                    player.dropItem(ModItems.LIFESTEAL_HEART.getDefaultStack(),false);
                }
                // if the players current health is greater than their max health, it sets it so they match
                // this is to remove a visual bug that happens
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
