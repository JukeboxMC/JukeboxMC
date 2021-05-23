package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPiston;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPiston extends Item {

    public ItemPiston() {
        super( 33 );
    }

    @Override
    public BlockPiston getBlock() {
        return new BlockPiston();
    }
}
