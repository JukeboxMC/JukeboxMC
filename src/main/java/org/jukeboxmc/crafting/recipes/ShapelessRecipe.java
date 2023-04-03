package org.jukeboxmc.crafting.recipes;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ShapelessRecipe extends Recipe {

    private final List<ItemDescriptorWithCount> ingredients = new ObjectArrayList<>();
    private final List<ItemData> outputs = new ObjectArrayList<>();

    @Override
    public List<ItemData> getOutputs() {
        return this.outputs;
    }

    public ShapelessRecipe addIngredient( Item... items ) {
        List<ItemDescriptorWithCount> itemDataList = new ArrayList<>();
        for ( Item item : items ) {
            itemDataList.add( ItemDescriptorWithCount.fromItem( item.toItemData() ) );
        }
        this.ingredients.addAll( itemDataList );
        return this;
    }

    public ShapelessRecipe addOutput( Item... items ) {
        List<ItemData> itemDataList = new ArrayList<>();
        for ( Item item : items ) {
            itemDataList.add( item.toItemData() );
        }
        this.outputs.addAll( itemDataList );
        return this;
    }

    @Override
    public RecipeData doRegister( CraftingManager craftingManager, String recipeId ) {
       return ShapelessRecipeData.shapeless( recipeId, ingredients, this.outputs, UUID.randomUUID(), "crafting_table", 1, craftingManager.getHighestNetworkId() + 1);

    }

}