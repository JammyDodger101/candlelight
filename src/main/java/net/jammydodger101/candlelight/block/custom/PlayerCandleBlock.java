package net.jammydodger101.candlelight.block.custom;

import com.mojang.serialization.MapCodec;
import net.jammydodger101.candlelight.util.ModTags;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PlayerCandleBlock

        extends AbstractCandleBlock

        implements Waterloggable {
    public static IntProperty CANDLES;
    public static BooleanProperty LIT;

    public static BooleanProperty WATERLOGGED;
    public static VoxelShape SHAPE;

    @Override
    protected MapCodec<? extends AbstractCandleBlock> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CANDLES, LIT, WATERLOGGED});
    }

    public PlayerCandleBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.getStateManager().getDefaultState()).with(CANDLES, 1)).with(LIT, false)).with(WATERLOGGED, false));
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return null;
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isEmpty() && state.get(LIT)) {
            CandleBlock.extinguish(player, state, world, pos);

        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(WATERLOGGED) || fluidState.getFluid() != Fluids.WATER) {
            return false;
        }

        BlockState blockState = (BlockState)state.with(WATERLOGGED, true);
        if (state.get(LIT)) {
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

        if (!state.get(LIT)) {
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

    static {
        CANDLES = Properties.CANDLES;
        LIT = AbstractCandleBlock.LIT;
        WATERLOGGED = Properties.WATERLOGGED;
        SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);

    }

}
