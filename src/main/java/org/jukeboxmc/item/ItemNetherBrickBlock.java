package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherBrickBlock extends Item {

    public ItemNetherBrickBlock() {
        super( 112 );
    }

    @Override
    public BlockNetherBrickBlock getBlock() {
        return new BlockNetherBrickBlock();
    }
}
