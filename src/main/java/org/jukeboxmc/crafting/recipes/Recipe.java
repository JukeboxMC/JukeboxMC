package org.jukeboxmc.crafting.recipes;

import lombok.ToString;
import org.jukeboxmc.item.Item;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Recipe {

    private final List<Item> input;
    private final Item output;

    public Recipe( List<Item> input, Item output ) {
        this.input = input;
        this.output = output;
    }

    public List<Item> getInput() {
        return this.input;
    }

    public Item getOutput() {
        return this.output;
    }
}
