package net.jammydodger101.candlelight.block.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.util.ModTags;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

public class PlayerCandleBlock

        extends CandleBlock

        implements Waterloggable {

    public static final int field_31050 = 1;
    public static final int MAX_CANDLE_AMOUNT = 1;
    public static final IntProperty CANDLES = Properties.CANDLES;
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    private static final VoxelShape ONE_CANDLE_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;




    private static final Int2ObjectMap<List<Vec3d>> CANDLES_TO_PARTICLE_OFFSETS = Util.make(() -> {
        Int2ObjectOpenHashMap int2ObjectMap = new Int2ObjectOpenHashMap();
        int2ObjectMap.defaultReturnValue(ImmutableList.of());
        int2ObjectMap.put(1, ImmutableList.of(new Vec3d(0.5, 0.5, 0.5)));
        int2ObjectMap.put(2, ImmutableList.of(new Vec3d(0.375, 0.44, 0.5), new Vec3d(0.625, 0.5, 0.44)));
        int2ObjectMap.put(3, ImmutableList.of(new Vec3d(0.5, 0.313, 0.625), new Vec3d(0.375, 0.44, 0.5), new Vec3d(0.56, 0.5, 0.44)));
        int2ObjectMap.put(4, ImmutableList.of(new Vec3d(0.44, 0.313, 0.56), new Vec3d(0.625, 0.44, 0.56), new Vec3d(0.375, 0.44, 0.375), new Vec3d(0.56, 0.5, 0.375)));
        return Int2ObjectMaps.unmodifiable(int2ObjectMap);
    });

    public PlayerCandleBlock(Settings settings, String name) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(CANDLES, 1)).with(LIT, false)).with(WATERLOGGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isEmpty() && state.get(LIT).booleanValue()) {


            CandleBlock.extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        } //else if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).getItem(). && state.get(LIT).booleanValue()) {

        //}



        return ActionResult.PASS;
    }



    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(WATERLOGGED).booleanValue() || fluidState.getFluid() != Fluids.WATER) {
            return false;
        }

        BlockState blockState = (BlockState)state.with(WATERLOGGED, true);
        if (state.get(LIT).booleanValue()) {
            //PlayerCandleHandler.changeCandleStatus(state.getBlock(), !isLitCandle(state));
            CandleBlock.extinguish(null, blockState, world, pos);
        } else {
            world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
        }
        world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
        return true;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if (!state.get(LIT).booleanValue()) {
            return;
        }

        this.getParticleOffsets(state).forEach(offset -> PlayerCandleBlock.spawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        PlayerCandleHandler.changeCandleStatus(state.getBlock(), state.get(LIT), world);

        if (world.getBlockState(pos).isIn(ModTags.Blocks.CUSTOM_CANDLES)) {
            world.scheduleBlockTick(pos, state.getBlock(), 1);
        }

    }


    public static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17f) {
                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        PlayerCandleHandler.setCandleCoordinates(pos, state, this, world);
        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

}
