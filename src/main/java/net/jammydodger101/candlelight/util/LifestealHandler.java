package net.jammydodger101.candlelight.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;

public class LifestealHandler {
    public static void increaseHealth(LivingEntity user, Integer amount) {
        if (user.getMaxHealth() < 40f) {
            if ((user.getMaxHealth() + amount) <= 40f) {
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth()+amount);
            }
        }

    }

    public static void decreaseHealth(LivingEntity user, Integer amount) {
        if (user.getMaxHealth() > 0f) {
            if ((user.getMaxHealth() - amount) > 0f) {
                user.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getMaxHealth() - amount);
            }
        } else {
            user.sendMessage(Text.literal("banned"));
        }
    }
}
