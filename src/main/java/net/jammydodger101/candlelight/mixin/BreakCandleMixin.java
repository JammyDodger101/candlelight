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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class BreakCandleMixin{


    @Inject(method = "onStacksDropped", at = @At("HEAD"))
    public void checkIfCandleBroke(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience, CallbackInfo ci) {
        if(!world.isClient()){
            //PlayerEntity player = world.getRandomAlivePlayer();
            //player.sendMessage(Text.literal("never break that block ever again liberal"));

            if (state.isIn(ModTags.Blocks.CUSTOM_CANDLES)) {
                //player.sendMessage(Text.literal("candle broken"));
                for (Block candleBlock :
                        PlayerCandleHandler.candles) {
                    if (state.isOf(candleBlock)) {
                        PlayerCandleHandler.candleStatus.set(PlayerCandleHandler.candles.indexOf(candleBlock), false);
                    }
                }

            }
        }
    }
}
