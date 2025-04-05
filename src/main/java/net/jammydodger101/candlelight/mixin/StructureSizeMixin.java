package net.jammydodger101.candlelight.mixin;

import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StructurePoolBasedGenerator.class)
public class StructureSizeMixin {

    @ModifyVariable(method = "generate(Lnet/minecraft/world/gen/structure/Structure$Context;Lnet/minecraft/registry/entry/RegistryEntry;Ljava/util/Optional;ILnet/minecraft/util/math/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int hi(int value){
        return 100;
    }

    @ModifyVariable(method = "generate(Lnet/minecraft/world/gen/structure/Structure$Context;Lnet/minecraft/registry/entry/RegistryEntry;Ljava/util/Optional;ILnet/minecraft/util/math/BlockPos;ZLjava/util/Optional;I)Ljava/util/Optional;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int hi2(int value){
        return 500;
    }
}
