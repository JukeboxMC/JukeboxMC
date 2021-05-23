package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonPlanks extends Item {

    public ItemCrimsonPlanks() {
        super( -242 );
    }

    @Override
    public BlockCrimsonPlanks getBlock() {
        return new BlockCrimsonPlanks();
    }
}
