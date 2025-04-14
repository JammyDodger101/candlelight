package net.jammydodger101.candlelight.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/*
Basically just crocksmarter blade functionality
 */

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand,
                                 Entity entity, @Nullable EntityHitResult hitResult) {

        // if hit by crocksmarter blade, either disconnects the player or removes the entity
        if (player.getStackInHand(hand).getItem() == ModItems.CROCKSMARTER_BLADE) {
            if (entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) entity).networkHandler.disconnect(Text.literal("You were struck by crocksmarter's power"));
            }else {
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }

        return ActionResult.PASS;
    }
}
