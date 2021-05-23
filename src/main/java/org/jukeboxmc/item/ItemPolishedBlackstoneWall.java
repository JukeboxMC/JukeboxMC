package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedBlackstoneWall;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedBlackstoneWall extends Item {

    public ItemPolishedBlackstoneWall() {
        super( -297 );
    }

    @Override
    public BlockPolishedBlackstoneWall getBlock() {
        return new BlockPolishedBlackstoneWall();
    }
}
