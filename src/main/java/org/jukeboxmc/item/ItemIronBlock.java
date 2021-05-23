package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronBlock extends Item {

    public ItemIronBlock() {
        super( 42 );
    }

    @Override
    public BlockIronBlock getBlock() {
        return new BlockIronBlock();
    }
}
