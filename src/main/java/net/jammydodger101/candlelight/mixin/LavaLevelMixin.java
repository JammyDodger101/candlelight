package net.jammydodger101.candlelight.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public class LavaLevelMixin {

    @Inject(method = "createFluidLevelSampler", at = @At("RETURN"), cancellable = true)
    private static void lowerFluidLevel(ChunkGeneratorSettings settings, CallbackInfoReturnable<AquiferSampler.FluidLevelSampler> cir) {
        cir.setReturnValue((x, y, z) -> {
            AquiferSampler.FluidLevel fluidLevel4 = new AquiferSampler.FluidLevel(0, Blocks.LAVA.getDefaultState());
            int i = settings.seaLevel();
            AquiferSampler.FluidLevel fluidLevel5 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
            return y < Math.min(0, i) ? fluidLevel4 : fluidLevel5;
        });
    }

}
