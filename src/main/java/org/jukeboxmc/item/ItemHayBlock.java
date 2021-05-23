package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHayBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHayBlock extends Item {

    public ItemHayBlock() {
        super( 170 );
    }

    @Override
    public BlockHayBlock getBlock() {
        return new BlockHayBlock();
    }
}
