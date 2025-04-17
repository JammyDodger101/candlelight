package net.jammydodger101.candlelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.jammydodger101.candlelight.block.ModBlocks;
import net.jammydodger101.candlelight.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {

                createShaped(RecipeCategory.TOOLS, ModItems.REVIVER, 1)
                        .pattern("CGC")
                        .pattern("DTD")
                        .pattern("CGC")
                        .input('C', Blocks.CRYING_OBSIDIAN)
                        .input('G', Blocks.GOLD_BLOCK)
                        .input('D', Items.DIAMOND)
                        .input('T', Items.TOTEM_OF_UNDYING)
                        .criterion(hasItem(Items.TOTEM_OF_UNDYING), conditionsFromItem(Items.TOTEM_OF_UNDYING))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CANDLE_COMPASS, 1)
                        .pattern("FFF")
                        .pattern("FCF")
                        .pattern("FFF")
                        .input('F', ModItems.EVENT_FRAGMENT)
                        .input('C', Items.COMPASS)
                        .criterion(hasItem(ModItems.EVENT_FRAGMENT), conditionsFromItem(ModItems.EVENT_FRAGMENT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.LIFESTEAL_HEART, 1)
                        .pattern("SFS")
                        .pattern("FRF")
                        .pattern("SFS")
                        .input('F', ModItems.EVENT_FRAGMENT)
                        .input('S', Items.NETHERITE_SCRAP)
                        .input('R', Blocks.RESPAWN_ANCHOR)
                        .criterion(hasItem(ModItems.EVENT_FRAGMENT), conditionsFromItem(ModItems.EVENT_FRAGMENT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.CROCKSMARTER_BLADE, 1)
                        .pattern(" C ")
                        .pattern(" C ")
                        .pattern(" S ")
                        .input('C', ModBlocks.CANDLELESS_FLOOR_BLOCK)
                        .input('S', Items.STICK)
                        .criterion(hasItem(ModBlocks.CANDLELESS_FLOOR_BLOCK), conditionsFromItem(ModBlocks.CANDLELESS_FLOOR_BLOCK))
                        .offerTo(exporter);

            }
        };
    }

    // creates recipes for everything except player compasses (not added yet)



    @Override
    public String getName() {
        return "";
    }
}
