package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/*
Handles the logic behind increasing and decreasing health
 */

public class LifestealHandler {
    public static void increaseHealth(LivingEntity user, Integer amount) {
        if (user.getMaxHealth() < 40f) {
            // makes sure the user is capped at 40 health
            if ((user.getMaxHealth() + amount) <= 40f) {
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth()+amount);
            }
        } else {
            if (user instanceof ServerPlayerEntity player) {
                // if at max health, it tries to give the heart to player
                // if player's inventory is full, it will drop the heart
                boolean wasAdded = player.getInventory().insertStack(ModItems.LIFESTEAL_HEART.getDefaultStack());
                if (!wasAdded) {
                    player.dropItem(ModItems.LIFESTEAL_HEART.getDefaultStack(),false);
                }
            }
        }

    }

    public static void decreaseHealth(LivingEntity user, Integer amount) {
        if (user.getMaxHealth() > 0f) {
            if ((user.getMaxHealth() - amount) > 0f) {
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth() - amount);
            }
        } else {
            // wont ever reach here as the lifesteal kill mixin will ban the player first
            user.sendMessage(Text.literal("banned"));
        }
    }
}
