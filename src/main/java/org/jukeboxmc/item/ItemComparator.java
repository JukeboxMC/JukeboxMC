package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCompartor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemComparator extends Item {

    public ItemComparator() {
        super( "minecraft:comparator", 512 );
    }

    @Override
    public BlockCompartor getBlock() {
        return new BlockCompartor();
    }
}
