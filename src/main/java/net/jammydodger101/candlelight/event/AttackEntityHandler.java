package net.jammydodger101.candlelight.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand,
                                 Entity entity, @Nullable EntityHitResult hitResult) {

        if (player.getStackInHand(hand).getItem() == ModItems.CROCKSMARTER_BLADE) {
            //new PlayerCandleHandler().checkCandleStatus(player);
            if (entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) entity).networkHandler.disconnect(Text.literal("crocksmarter's power"));
            }
        }

        return ActionResult.PASS;
    }
}
