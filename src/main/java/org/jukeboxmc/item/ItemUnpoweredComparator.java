package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCompartor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnpoweredComparator extends Item {

    public ItemUnpoweredComparator() {
        super ( "minecraft:unpowered_comparator" );
    }

    @Override
    public BlockCompartor getBlock() {
        return new BlockCompartor();
    }
}
