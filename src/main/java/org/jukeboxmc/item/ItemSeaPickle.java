package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSeaPickle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSeaPickle extends Item {

    public ItemSeaPickle() {
        super( -156 );
    }

    @Override
    public BlockSeaPickle getBlock() {
        return new BlockSeaPickle();
    }
}
