package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCommandBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCommandBlock extends Item {

    public ItemCommandBlock() {
        super( 137 );
    }

    @Override
    public BlockCommandBlock getBlock() {
        return new BlockCommandBlock();
    }
}
