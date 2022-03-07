package org.jukeboxmc.crafting.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.item.Item;

import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CraftingData {

    private final CraftingDataType type;
    private final String recipeId;
    private final int width;
    private final int height;
    private final int inputId;
    private final int inputDamage;
    private final List<Item> inputs;
    private final List<Item> outputs;
    private final UUID uuid;
    private final String craftingTag;
    private final int priority;
    private final int networkId;

}
