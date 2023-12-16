package net.jammydodger101.candlelight.block.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;

public class PlayerCandleBlock
        extends CandleBlock
        implements Waterloggable {

    public static final int field_31050 = 1;
    public static final int MAX_CANDLE_AMOUNT = 1;
    public static final IntProperty CANDLES = Properties.CANDLES;
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    private static final VoxelShape ONE_CANDLE_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);

    public String PLAYER_NAME = null;


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
        PLAYER_NAME = name;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        PlayerCandleHandler.changeCandleStatus(state.getBlock(), !isLitCandle(state));
        if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isEmpty() && state.get(LIT).booleanValue()) {


            CandleBlock.extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(CANDLES) < 4) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState)blockState.cycle(CANDLES);
        }
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return (BlockState)super.getPlacementState(ctx).with(WATERLOGGED, bl);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ONE_CANDLE_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, WATERLOGGED);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(WATERLOGGED).booleanValue() || fluidState.getFluid() != Fluids.WATER) {
            return false;
        }
        BlockState blockState = (BlockState)state.with(WATERLOGGED, true);
        if (state.get(LIT).booleanValue()) {
            CandleBlock.extinguish(null, blockState, world, pos);
        } else {
            world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
        }
        world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
        return true;
    }

    public static boolean canBeLit(BlockState state) {
        return state.isIn(BlockTags.CANDLES, statex -> statex.contains(LIT) && statex.contains(WATERLOGGED)) && state.get(LIT) == false && state.get(WATERLOGGED) == false;
    }

    public boolean playerSurvival(String playerName, BlockState state) {
        return state.get(WATERLOGGED) == false && super.isNotLit(state);
    }

    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return (Iterable)CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES));
    }

    @Override
    protected boolean isNotLit(BlockState state) {
        return state.get(WATERLOGGED) == false && super.isNotLit(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }


}
