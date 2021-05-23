package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCocoa;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCocoa extends Item {

    public ItemCocoa() {
        super( 127 );
    }

    @Override
    public BlockCocoa getBlock() {
        return new BlockCocoa();
    }
}
