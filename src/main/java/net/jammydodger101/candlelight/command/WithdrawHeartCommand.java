package net.jammydodger101.candlelight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.LifestealHandler;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class WithdrawHeartCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("withdraw")
                //.then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                        .executes(WithdrawHeartCommand::run));

    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayer();
        //int withdrawAmount = IntegerArgumentType.getInteger(context, "amount");
        if (player.getMaxHealth() > 0f) {
            if ((player.getMaxHealth() - 2) > 0f) {
                player.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(player.getMaxHealth() - 2);
                //player.giveItemStack(ModItems.LIFESTEAL_HEART.getDefaultStack());
                boolean wasAdded = player.getInventory().insertStack(ModItems.LIFESTEAL_HEART.getDefaultStack());
                if (!wasAdded) {
                    player.dropItem(ModItems.LIFESTEAL_HEART.getDefaultStack(),false);
                }
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        } else {
            player.sendMessage(Text.literal("ummmm,..,..... dead! blehhhh X333"));
            return -1;
        }
        return 1;
    }
}
