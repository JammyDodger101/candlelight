package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REVIVER, 1)
                .pattern("CGC")
                .pattern("DTD")
                .pattern("CGC")
                .input('C', Blocks.CRYING_OBSIDIAN)
                .input('G', Blocks.GOLD_BLOCK)
                .input('D', Blocks.DIAMOND_BLOCK)
                .input('T', Items.TOTEM_OF_UNDYING)
                .criterion(hasItem(Items.TOTEM_OF_UNDYING), conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.REVIVER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CANDLE_COMPASS, 1)
                .pattern("FFF")
                .pattern("FCF")
                .pattern("FFF")
                .input('F', ModItems.EVENT_FRAGMENT)
                .input('C', Items.COMPASS)
                .criterion(hasItem(ModItems.EVENT_FRAGMENT), conditionsFromItem(ModItems.EVENT_FRAGMENT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CANDLE_COMPASS)));
    }
}
