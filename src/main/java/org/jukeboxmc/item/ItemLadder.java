package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLadder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLadder extends Item {

    public ItemLadder() {
        super( 65 );
    }

    @Override
    public BlockLadder getBlock() {
        return new BlockLadder();
    }
}
