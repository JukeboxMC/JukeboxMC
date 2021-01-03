package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonPlanks extends Item {

    public ItemCrimsonPlanks() {
        super( "minecraft:crimson_planks", -242 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonPlanks();
    }
}
