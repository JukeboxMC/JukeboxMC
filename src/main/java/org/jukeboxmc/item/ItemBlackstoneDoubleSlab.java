package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneDoubleSlab extends Item {

    public ItemBlackstoneDoubleSlab() {
        super( -283 );
    }

    @Override
    public BlockDoubleBlackstoneSlab getBlock() {
        return new BlockDoubleBlackstoneSlab();
    }
}
