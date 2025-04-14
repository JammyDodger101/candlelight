package net.jammydodger101.candlelight.item.custom;

import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.CandleCompassFunctionality;
import net.jammydodger101.candlelight.util.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/*
A compass that points to the nearest candle when used
 */

public class CandleCompassItem
        extends Item {

    // randomly assigns a distance from the candle where the compass breaks
    public static Integer trackingDistance = new Random().nextInt(400,600);

    public CandleCompassItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    // only returns true if the coordinate data component actually has a position stored
    public static boolean hasCandle(ItemStack stack) {
        try {
            return stack.get(ModDataComponentTypes.COORDINATES).pos() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    // returns the candle position
    @Nullable
    public static GlobalPos createCandlePos(ItemStack stack) {
        return stack.get(ModDataComponentTypes.COORDINATES);
    }

    // returns the position of spawn
    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    // only makes the candle glint if it has a position stored
    @Override
    public boolean hasGlint(ItemStack stack) {
        return CandleCompassItem.hasCandle(stack) || super.hasGlint(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }

        if (CandleCompassItem.hasCandle(stack)) {
            GlobalPos globalPos = stack.get(ModDataComponentTypes.COORDINATES);
            assert globalPos != null;
            BlockPos blockPos = globalPos.pos();
            RegistryKey<World> optional = globalPos.dimension();

            // if the candle it's tracking is no longer there, it sets the location its tracking to the integer limit
            if (optional == world.getRegistryKey()
                    && (!world.isInBuildLimit(blockPos)
                    || !world.getBlockState(blockPos).isIn(ModTags.Blocks.CUSTOM_CANDLES)))
                     {
                stack.set(ModDataComponentTypes.COORDINATES, GlobalPos.create(world.getRegistryKey(), CandleCompassFunctionality.intLimitPos));

                // if the candle is at the integer limit, it will try and point to the nearest candle it can
                if (blockPos.getY() > 200000) {
                    CandleCompassFunctionality.fillCandleCoordinates(world);
                    GlobalPos nearestCandle = CandleCompassFunctionality.getNearestCandle((PlayerEntity) entity, world);
                    stack.set(ModDataComponentTypes.COORDINATES, nearestCandle);
                }

            }

            // if the player location is closer to the block than the tracking distance, the compass breaks
            if (blockPos.getSquaredDistance(entity.getBlockPos().toCenterPos()) < trackingDistance) {
                stack.decrement(1);
                world.playSound(null, entity.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // resets the tracking distance so its not the same everytime
        trackingDistance = new Random().nextInt(400,600);

        // fills out the candle coordinates in the functionality class
        CandleCompassFunctionality.fillCandleCoordinates(world);

        // gets the nearest candle
        GlobalPos blockPos = CandleCompassFunctionality.getNearestCandle(user, world);

        // if there are no candles, the use action fails
        if (blockPos == null) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1.0f, 1.0f);

        // if creative, gives the player an extra compass instead of using up the original
        // otherwise, it just updates the current compass to point in the right direction
        ItemStack itemStack = user.getStackInHand(hand);
        boolean isCreative = !user.getAbilities().creativeMode && itemStack.getCount() == 1;
        if (isCreative) {
            itemStack.set(ModDataComponentTypes.COORDINATES, blockPos);
        } else {
            ItemStack itemStack2 = itemStack.copyComponentsToNewStack(ModItems.CANDLE_COMPASS, 1);
            itemStack.decrementUnlessCreative(1, user);
            itemStack2.set(ModDataComponentTypes.COORDINATES, blockPos);
            // drops the item if the players inventory is full
            if (!user.getInventory().insertStack(itemStack2)) {
                user.dropItem(itemStack2, false);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return CandleCompassItem.hasCandle(stack) ? "item.candlelight.candle_compass" : super.getTranslationKey(stack);
    }
}
