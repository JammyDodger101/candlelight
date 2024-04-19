package net.jammydodger101.candlelight.item.custom;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.jammydodger101.candlelight.Candlelight;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.ModItems;
import net.jammydodger101.candlelight.util.CandleCompassFunctionality;
import net.jammydodger101.candlelight.util.ModTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.Random;

public class CandleCompassItem
        extends Item
        implements Vanishable {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String CANDLE_POS_KEY = "CandlePos";
    public static final String CANDLE_DIMENSION_KEY = "CandleDimension";
    public static final String CANDLE_TRACKED_KEY = "CandleTracked";

    public static final Integer trackingDistance = new Random().nextInt(400,600);

    public CandleCompassItem(Settings settings) {
        super(settings);
    }

    public static boolean hasCandle(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && (nbtCompound.contains(CANDLE_DIMENSION_KEY) || nbtCompound.contains(CANDLE_POS_KEY));
    }

    private static Optional<RegistryKey<World>> getCandleDimension(NbtCompound nbt) {
        return World.CODEC.parse(NbtOps.INSTANCE, nbt.get(CANDLE_DIMENSION_KEY)).result();
    }

    @Nullable
    public static GlobalPos createCandlePos(NbtCompound nbt) {
        Optional<RegistryKey<World>> optional;
        boolean bl = nbt.contains(CANDLE_POS_KEY);
        boolean bl2 = nbt.contains(CANDLE_DIMENSION_KEY);
        if (bl && bl2 && (optional = CandleCompassItem.getCandleDimension(nbt)).isPresent()) {
            BlockPos blockPos = NbtHelper.toBlockPos(nbt.getCompound(CANDLE_POS_KEY));
            return GlobalPos.create(optional.get(), blockPos);
        }
        return null;
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


            BlockPos blockPos;
            NbtCompound nbtCompound = stack.getOrCreateNbt();
            if (nbtCompound.contains(CANDLE_TRACKED_KEY) && !nbtCompound.getBoolean(CANDLE_TRACKED_KEY)) {
                return;
            }
            Optional<RegistryKey<World>> optional = CandleCompassItem.getCandleDimension(nbtCompound);

            //code to make it spin if theres no candle anymore?? i think??
            if (optional.isPresent() && optional.get() == world.getRegistryKey()
                    && nbtCompound.contains(CANDLE_POS_KEY)
                    && (!world.isInBuildLimit(blockPos = NbtHelper.toBlockPos(nbtCompound.getCompound(CANDLE_POS_KEY)))
                    || !world.getBlockState(blockPos).isIn(ModTags.Blocks.CUSTOM_CANDLES)))
                     {
                nbtCompound.remove(CANDLE_POS_KEY);

                if (blockPos.getY() < 200000) {
                    CandleCompassFunctionality.fillCandleCoordinates(world);
                    BlockPos nearestCandle = CandleCompassFunctionality.getNearestCandle((PlayerEntity) entity);
                    this.writeNbt(world.getRegistryKey(), nearestCandle, stack.getOrCreateNbt());
                }

            }

            blockPos = NbtHelper.toBlockPos(nbtCompound.getCompound(CANDLE_POS_KEY));
            if (blockPos.getSquaredDistance(entity.getBlockPos().toCenterPos()) < trackingDistance) {
                stack.decrement(1);
                //world.getServer().getWorld(world.getServer().getOverworld().getRegistryKey()).spawnParticles(
                        //ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getY(), entity.getZ(), 1000, 1.0f,1.0f,1.0f,1.0f);
                world.playSound(null, entity.getBlockPos(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                //world.addParticle(ParticleTypes.HEART, entity.getX(), entity.getY()+2.0, entity.getZ(),0.0f,0.0f,0.0f);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //BlockPos blockPos = user.getBlockPos();


        CandleCompassFunctionality.fillCandleCoordinates(world);

        BlockPos blockPos = CandleCompassFunctionality.getNearestCandle(user);

        //if (world.getBlockState(blockPos).isOf(ModBlocks.JAMMY_CANDLE)) {
        boolean bl;
        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1.0f, 1.0f);

        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl2 = bl = !user.getAbilities().creativeMode && itemStack.getCount() == 1;
        if (bl) {
            this.writeNbt(world.getRegistryKey(), blockPos, itemStack.getOrCreateNbt());
        } else {
            ItemStack itemStack2 = new ItemStack(ModItems.CANDLE_COMPASS, 1);
            NbtCompound nbtCompound = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
            itemStack2.setNbt(nbtCompound);
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            this.writeNbt(world.getRegistryKey(), blockPos, nbtCompound);
            if (!user.getInventory().insertStack(itemStack2)) {
                user.dropItem(itemStack2, false);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }


    private void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt) {
        nbt.put(CANDLE_POS_KEY, NbtHelper.fromBlockPos(pos));
        World.CODEC.encodeStart(NbtOps.INSTANCE, worldKey).resultOrPartial(LOGGER::error).ifPresent(nbtElement -> nbt.put(CANDLE_DIMENSION_KEY, (NbtElement)nbtElement));
        nbt.putBoolean(CANDLE_TRACKED_KEY, true);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return CandleCompassItem.hasCandle(stack) ? "item.candlelight.candle_compass" : super.getTranslationKey(stack);
    }
}
