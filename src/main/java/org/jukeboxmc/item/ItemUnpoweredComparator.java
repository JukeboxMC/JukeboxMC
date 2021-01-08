package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUnpoweredCompartor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnpoweredComparator extends Item {

    public ItemUnpoweredComparator() {
        super( "minecraft:unpowered_comparator", 149 );
    }

    @Override
    public BlockUnpoweredCompartor getBlock() {
        return new BlockUnpoweredCompartor();
    }
}
