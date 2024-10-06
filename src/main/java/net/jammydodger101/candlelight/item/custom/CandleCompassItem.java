package net.jammydodger101.candlelight.item.custom;

import com.mojang.logging.LogUtils;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.component.ModDataComponentTypes;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.CandleCompassFunctionality;
import net.jammydodger101.candlelight.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class CandleCompassItem
        extends Item {

    public static Integer trackingDistance = new Random().nextInt(400,600);

    public CandleCompassItem(Settings settings) {
        super(settings);
    }

    public static boolean hasCandle(ItemStack stack) {
        try {
            return stack.get(ModDataComponentTypes.COORDINATES).pos() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Nullable
    public static GlobalPos createCandlePos(ItemStack stack) {
        return stack.get(ModDataComponentTypes.COORDINATES);
    }

    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

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
            //NbtCompound nbtCompound = stack.get(ModItemComponents.CANDLE_COMPASS_DATA);
            assert globalPos != null;
            BlockPos blockPos = globalPos.pos();
            RegistryKey<World> optional = globalPos.dimension();

            //code to make it spin if theres no candle anymore?? i think??
            if (optional == world.getRegistryKey()
                    && (!world.isInBuildLimit(globalPos.pos())
                    || !world.getBlockState(blockPos).isIn(ModTags.Blocks.CUSTOM_CANDLES)))
                     {
                stack.set(ModDataComponentTypes.COORDINATES, GlobalPos.create(world.getRegistryKey(), CandleCompassFunctionality.intLimitPos));

                if (blockPos.getY() > 200000) {
                    CandleCompassFunctionality.fillCandleCoordinates(world);
                    GlobalPos nearestCandle = CandleCompassFunctionality.getNearestCandle((PlayerEntity) entity, world);
                    stack.set(ModDataComponentTypes.COORDINATES, nearestCandle);
                }

            }

            if (blockPos.getSquaredDistance(entity.getBlockPos().toCenterPos()) < trackingDistance) {
                stack.decrement(1);
                world.playSound(null, entity.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //BlockPos blockPos = user.getBlockPos();

        trackingDistance = new Random().nextInt(400,600);

        CandleCompassFunctionality.fillCandleCoordinates(world);

        GlobalPos blockPos = CandleCompassFunctionality.getNearestCandle(user, world);

        if (blockPos == null) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        //if (world.getBlockState(blockPos).isOf(ModBlocks.JAMMY_CANDLE)) {
        boolean bl;
        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1.0f, 1.0f);

        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl2 = bl = !user.getAbilities().creativeMode && itemStack.getCount() == 1;
        if (bl) {
            itemStack.set(ModDataComponentTypes.COORDINATES, blockPos);
        } else {
            ItemStack itemStack2 = itemStack.copyComponentsToNewStack(ModItems.CANDLE_COMPASS, 1);
            itemStack.decrementUnlessCreative(1, user);
            itemStack2.set(ModDataComponentTypes.COORDINATES, blockPos);
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
