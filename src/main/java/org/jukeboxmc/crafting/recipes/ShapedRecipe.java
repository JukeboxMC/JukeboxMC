package org.jukeboxmc.crafting.recipes;

import com.nukkitx.protocol.bedrock.data.inventory.CraftingData;
import com.nukkitx.protocol.bedrock.data.inventory.CraftingDataType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ShapedRecipe extends Recipe {

    private final Char2ObjectMap<ItemDescriptorWithCount> ingredients = new Char2ObjectOpenHashMap<>();
    private final List<ItemData> outputs = new ObjectArrayList<>();
    private String[] pattern;

    @Override
    public List<ItemData> getOutputs() {
        return this.outputs;
    }

    public ShapedRecipe shape( String... pattern ) {
        if ( pattern.length > 3 || pattern.length < 1 ) {
            throw new RuntimeException( "Shape height must be between 3 and 1!" );
        }

        String first = pattern[0];

        if ( first.length() > 3 || first.length() < 1 ) {
            throw new RuntimeException( "Shape width must be between 3 and 1!" );
        }

        int last = first.length();

        for ( String line : pattern ) {
            if ( line.length() != last ) {
                throw new RuntimeException( "Shape colums must be have the same length!" );
            }
        }

        this.pattern = pattern;
        return this;
    }

    public ShapedRecipe setIngredient( char symbol, Item item ) {
        this.ingredients.put( symbol, ItemDescriptorWithCount.fromItem( item.toItemData() ));
        return this;
    }

    public ShapedRecipe addOutput( Item... items ) {
        List<ItemData> itemDataList = new ArrayList<>();
        for ( Item item : items ) {
            itemDataList.add( item.toItemData() );
        }
        this.outputs.addAll( itemDataList );
        return this;
    }

    @Override
    public CraftingData doRegister( CraftingManager craftingManager, String recipeId ) {
        final List<ItemDescriptorWithCount> ingredients = new ArrayList<>();
        for ( String s : this.pattern ) {
            char[] chars = s.toCharArray();
            for ( char c : chars ) {
                ItemDescriptorWithCount ingredient = this.ingredients.get( c );

                if ( c == ' ' ) {
                    ingredients.add( ItemDescriptorWithCount.EMPTY );
                    continue;
                }

                if ( ingredient == null ) {
                    return null;
                }

                ingredients.add( ingredient );
            }
        }

        return new CraftingData(
                CraftingDataType.SHAPED,
                recipeId,
                this.pattern[0].length(),
                this.pattern.length,
                -1,
                -1,
                ingredients,
                this.outputs,
                UUID.randomUUID(),
                "crafting_table",
                1,
                craftingManager.getHighestNetworkId() + 1
        );
    }

}