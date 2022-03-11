package org.jukeboxmc.crafting.recipes;

import lombok.ToString;
import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class SmeltingRecipe {

    private final Item input;
    private final Item output;

    public SmeltingRecipe( Item input, Item output ) {
        this.input = input;
        this.output = output;
    }

    public Item getInput() {
        return this.input;
    }

    public Item getOutput() {
        return this.output;
    }
}
