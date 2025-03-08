package net.jammydodger101.candlelight.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jammydodger101.candlelight.effect.ModEffects;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BackgroundRenderer.class)
public class ExtinguishedFogMixin {

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void applyFogWhenExtinguished(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();
        //BackgroundRenderer.FogData fogData = new BackgroundRenderer.FogData(fogType);
        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.EXTINGUISHED)) {
                RenderSystem.setShaderFogStart(0.0f);
                RenderSystem.setShaderFogEnd(15.0f);
                RenderSystem.setShaderFogColor(0f, 0f, 0f);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
                //RenderSystem.setShaderColor(38f, 33f, 123f, 0.1f);
                //RenderSystem.colorMask(false, true, false, true);
            }
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;"))
    private static void changeColours(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {

    }


}
