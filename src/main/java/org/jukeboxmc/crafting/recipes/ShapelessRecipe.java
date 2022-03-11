package org.jukeboxmc.crafting.recipes;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.crafting.data.CraftingData;
import org.jukeboxmc.crafting.data.CraftingDataType;
import org.jukeboxmc.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ShapelessRecipe extends Recipe {

    private final List<Item> ingredients = new ObjectArrayList<>();
    private final List<Item> outputs = new ObjectArrayList<>();

    @Override
    public List<Item> getOutputs() {
        return this.outputs;
    }

    public ShapelessRecipe addIngredient( Item... item ) {
        this.ingredients.addAll( Arrays.asList( item ) );
        return this;
    }

    public ShapelessRecipe addOutput( Item... items ) {
        this.outputs.addAll( Arrays.asList( items ) );
        return this;
    }

    @Override
    public CraftingData doRegister( CraftingManager craftingManager, String recipeId ) {
        return new CraftingData(
                CraftingDataType.SHAPELESS,
                recipeId,
                -1,
                -1,
                -1,
                -1,
                this.ingredients,
                this.outputs,
                UUID.randomUUID(),
                "crafting_table",
                1,
                0xDEADBEEF
        );
    }

}