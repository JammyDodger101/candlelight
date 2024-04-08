package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.util.ModTags;
import net.jammydodger101.candlelight.util.PlayerCandleHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public abstract class BreakCandleMixin{


    @Shadow protected abstract Block asBlock();

    @Inject(method = "onStateReplaced", at = @At("HEAD"))
    public void checkIfCandleBroke(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if(!world.isClient()){


            if (state.isIn(ModTags.Blocks.CUSTOM_CANDLES)) {

                for (Block candleBlock :
                        PlayerCandleHandler.candles) {
                    if (state.isOf(candleBlock)) {
                        if (!newState.isOf(this.asBlock())) {
                            PlayerCandleHandler.candleStatus.set(PlayerCandleHandler.candles.indexOf(candleBlock), false);
                            world.getPlayers().get(0).sendMessage(Text.literal("Candle is broken"));
                        }

                    }
                }
            }
        }
    }
}
