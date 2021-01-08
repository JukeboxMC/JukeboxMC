package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedPlanks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedPlanks extends Item {

    public ItemWarpedPlanks() {
        super( "minecraft:warped_planks", -243 );
    }

    @Override
    public BlockWarpedPlanks getBlock() {
        return new BlockWarpedPlanks();
    }
}
