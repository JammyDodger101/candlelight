package net.jammydodger101.candlelight.mixin;

import net.jammydodger101.candlelight.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistantData;

    @Override
    public NbtCompound getPersistantData() {
        if(this.persistantData == null) {
            this.persistantData = new NbtCompound();
        }

        return persistantData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable ci) {
        if(persistantData != null) {
            nbt.put("candlelight.candle_data", persistantData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("candlelight.candle_data", 10)){
            persistantData = nbt.getCompound("candlelight.candle_data");
        }
    }
}
