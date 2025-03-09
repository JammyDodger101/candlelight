package net.jammydodger101.candlelight.item.custom;

import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.util.ModTags;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerCompassItem extends Item {

    public static List<String> playerList = new ArrayList<>();

    public PlayerCompassItem(Settings settings) {
        super(settings);
    }

    public static boolean hasPlayer(ItemStack stack) {

        if (stack.get(ModDataComponentTypes.TARGET_PLAYER) != null) {
            return playerList.contains(stack.get(ModDataComponentTypes.TARGET_PLAYER));
        }
        return false;
    }

    public static boolean isPlayerOnline(String playerName, World world) {

        for (PlayerEntity player : world.getPlayers()) {
            if (player.getNameForScoreboard().toLowerCase().equals(playerName)) {
                return true;
            }
        }

        return false;
    }


    @Nullable
    public static GlobalPos createPlayerPos(ItemStack stack, World world) {
        //return stack.get(ModDataComponentTypes.TARGET_PLAYER);
        if (stack.get(ModDataComponentTypes.TARGET_PLAYER) != null) {
            String targetPlayer = stack.get(ModDataComponentTypes.TARGET_PLAYER);

            if (isPlayerOnline(targetPlayer, world)) {
                if (world.getPlayers().size() > playerList.indexOf(targetPlayer)) {
                    PlayerEntity targetPlayerEntity = world.getPlayers().get(playerList.indexOf(targetPlayer));
                    //targetPlayerEntity.sendMessage(Text.literal(String.valueOf(playerList.indexOf(targetPlayer))));
                    BlockPos playerPos = targetPlayerEntity.getBlockPos();
                    return GlobalPos.create(world.getRegistryKey(), playerPos);
                }

            }
        }


        return null;
    }

    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return PlayerCompassItem.hasPlayer(stack) || super.hasGlint(stack);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        if (world.isClient) {
            return ActionResult.PASS;
        }
        BlockState state = world.getBlockState(pos);
        if (!state.isIn(ModTags.Blocks.CUSTOM_CANDLES)) {
            return super.useOnBlock(context);
        } else {
            // get player related to candle
            // play sound
            String targetPlayer = PlayerCandleHandler.candleOwners.get(PlayerCandleHandler.getListLocation(state.getBlock()));
            if (isPlayerOnline(targetPlayer, world)) {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                ItemStack stack = context.getStack();
                stack.set(ModDataComponentTypes.TARGET_PLAYER, targetPlayer);
                return ActionResult.SUCCESS;
            }

        }
        return ActionResult.PASS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }

        // update player list
        playerList.clear();
        for (PlayerEntity player : world.getPlayers()) {
            playerList.add(player.getNameForScoreboard().toLowerCase());
        }

    }
}
