package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.util.ModTags;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
Removes the candle coordinate and status of a candle when it is broken
 */

@Mixin(AbstractBlock.class)
public abstract class BreakCandleMixin{


    @Shadow protected abstract Block asBlock();

    @Inject(method = "onStateReplaced", at = @At("HEAD"))
    public void checkIfCandleBroke(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if(!world.isClient()){
            // checks to make sure we are updating the correct candle
            if (state.isIn(ModTags.Blocks.CUSTOM_CANDLES)) {
                for (Block candleBlock :
                        PlayerCandleHandler.candles) {
                    if (state.isOf(candleBlock)) {
                        if (!newState.isOf(this.asBlock())) {
                            // sets lit status to false and coordinates to null
                            PlayerCandleHandler.candleStatus.set(PlayerCandleHandler.candles.indexOf(candleBlock), false);
                            PlayerCandleHandler.setCandleCoordinates(pos, state, null, world);
                        }
                    }
                }
            }
        }
    }
}
