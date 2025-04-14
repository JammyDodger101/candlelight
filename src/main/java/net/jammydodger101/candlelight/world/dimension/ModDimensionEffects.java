package net.jammydodger101.candlelight.world.dimension;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

/*
Unused class for the effects that should be applied in the Candleless dimension
Probably need to get this working someday
 */

public class ModDimensionEffects extends DimensionEffects{


    private static final Object2ObjectMap<Identifier, DimensionEffects> BY_IDENTIFIER = Util.make(new Object2ObjectArrayMap(), map -> {
        DimensionEffects.Overworld overworld = new DimensionEffects.Overworld();
        map.defaultReturnValue(overworld);
        map.put(Identifier.of("candleless"), new Candleless());

    });

    public ModDimensionEffects(float cloudsHeight, boolean alternateSkyColor, DimensionEffects.SkyType skyType, boolean brightenLighting, boolean darkened) {
        super(cloudsHeight, alternateSkyColor, skyType, brightenLighting, darkened);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return true;
    }

    @Environment(value= EnvType.CLIENT)
    public static class Candleless
            extends DimensionEffects {
        public Candleless() {
            super(Float.NaN, false, SkyType.END, false, true);
        }

        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color;
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return true;
        }

        @Override
        @Nullable
        public float[] getFogColorOverride(float skyAngle, float tickDelta) {
            return null;
        }
    }

    public static enum SkyType {
        CANDLELESS;

    }
}
