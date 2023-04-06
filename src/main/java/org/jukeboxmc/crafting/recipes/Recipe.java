package org.jukeboxmc.crafting.recipes;

import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData;
import org.jukeboxmc.crafting.CraftingManager;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Recipe {

    public abstract List<ItemData> getOutputs();

    public abstract RecipeData doRegister( CraftingManager craftingManager, String recipeId );

}