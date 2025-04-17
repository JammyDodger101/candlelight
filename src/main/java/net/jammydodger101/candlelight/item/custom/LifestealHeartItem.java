package net.jammydodger101.candlelight.item.custom;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/*
The physical hearts that can be withdrawn using the /withdraw command
 */

public class LifestealHeartItem extends Item {
    public LifestealHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        // doesnt let the player gain any more health if already at 40
        if (user.getMaxHealth() < 40f) {
            // adds a heart to the players health and removes the heart item
            user.getAttributes().getCustomInstance(EntityAttributes.MAX_HEALTH).setBaseValue(user.getMaxHealth()+2f);
            itemStack.decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
