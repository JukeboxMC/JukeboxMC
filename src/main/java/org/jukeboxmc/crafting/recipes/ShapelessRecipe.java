package org.jukeboxmc.crafting.recipes;

import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull List<ItemData> getOutputs() {
        return this.outputs;
    }

    public @NotNull ShapelessRecipe addIngredient(Item @NotNull ... items ) {
        List<ItemDescriptorWithCount> itemDataList = new ArrayList<>();
        for ( Item item : items ) {
            itemDataList.add( ItemDescriptorWithCount.fromItem( item.toItemData() ) );
        }
        this.ingredients.addAll( itemDataList );
        return this;
    }

    public @NotNull ShapelessRecipe addOutput(Item @NotNull ... items ) {
        List<ItemData> itemDataList = new ArrayList<>();
        for ( Item item : items ) {
            itemDataList.add( item.toItemData() );
        }
        this.outputs.addAll( itemDataList );
        return this;
    }

    @Override
    public @NotNull CraftingData doRegister(@NotNull CraftingManager craftingManager, String recipeId ) {
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
                craftingManager.getHighestNetworkId() + 1
        );
    }

}