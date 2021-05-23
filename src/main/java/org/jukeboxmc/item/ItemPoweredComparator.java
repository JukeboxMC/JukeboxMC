package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPoweredComparator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPoweredComparator extends Item {

    public ItemPoweredComparator() {
        super( 150 );
    }

    @Override
    public BlockPoweredComparator getBlock() {
        return new BlockPoweredComparator();
    }
}
