package org.jukeboxmc.crafting.recipes;

import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.RequiredArgsConstructor;
import org.jukeboxmc.crafting.CraftingManager;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Recipe {

    public abstract List<ItemData> getOutputs();

    public abstract CraftingData doRegister( CraftingManager craftingManager, String recipeId );

}