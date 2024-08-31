package net.jammydodger101.candlelight.item.custom;

import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class ReviverItem extends Item {
    public ReviverItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (world.getServer().getOverworld());
            if (serverWorld == null) {
                return TypedActionResult.fail(itemStack);
            }
            serverWorld.getServer().getPlayerManager().getPlayer(user.getNameForScoreboard());
            PlayerCandleHandler.reviveEveryone(serverWorld);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.isSpectator()) {
                itemStack.decrement(1);
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
