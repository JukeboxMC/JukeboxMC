package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockWaxedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedCutCopperStairs extends Item{

    public ItemWaxedCutCopperStairs() {
        super( "minecraft:waxed_cut_copper_stairs" );
    }

    @Override
    public Block getBlock() {
        return new BlockWaxedCutCopperStairs();
    }
}
