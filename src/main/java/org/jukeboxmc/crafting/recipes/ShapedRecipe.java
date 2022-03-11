package org.jukeboxmc.crafting.recipes;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.crafting.data.CraftingData;
import org.jukeboxmc.crafting.data.CraftingDataType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ShapedRecipe extends Recipe {

    private final Char2ObjectMap<Item> ingredients = new Char2ObjectOpenHashMap<>();
    private final List<Item> outputs = new ObjectArrayList<>();
    private String[] pattern;

    @Override
    public List<Item> getOutputs() {
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
        this.ingredients.put( symbol, item );
        return this;
    }

    public ShapedRecipe addOutput( Item... items ) {
        this.outputs.addAll( Arrays.asList(items) );
        return this;
    }

    @Override
    public CraftingData doRegister( CraftingManager craftingManager, String recipeId ) {
        final List<Item> ingredients = new ArrayList<>();
        for ( String s : this.pattern ) {
            char[] chars = s.toCharArray();
            for ( char c : chars ) {
                Item ingredient = this.ingredients.get( c );

                if ( c == ' ' ) {
                    ingredients.add( new ItemAir() );
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
                0xDEADBEEF
        );
    }

}