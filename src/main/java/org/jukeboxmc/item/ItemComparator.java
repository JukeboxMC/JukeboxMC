package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockUnpoweredCompartor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemComparator extends Item {

    public ItemComparator() {
        super("minecraft:comparator", 512);
    }

    @Override
    public Block getBlock() {
        return new BlockUnpoweredCompartor();
    }
}
