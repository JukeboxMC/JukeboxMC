package org.jukeboxmc.crafting.recipes;

import lombok.RequiredArgsConstructor;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.crafting.data.CraftingData;
import org.jukeboxmc.item.Item;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Recipe {

    public abstract List<Item> getOutputs();

    public abstract CraftingData doRegister( CraftingManager craftingManager, String recipeId );

}