package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneBrickSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneBrickSlab extends Item {

    public ItemPolishedBlackstoneBrickSlab() {
        super( -284 );
    }

    @Override
    public BlockPolishedBlackstoneBrickSlab getBlock() {
        return new BlockPolishedBlackstoneBrickSlab();
    }
}
