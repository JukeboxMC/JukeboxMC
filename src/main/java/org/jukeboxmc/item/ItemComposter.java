package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockComposter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemComposter extends Item {

    public ItemComposter() {
        super(  -213 );
    }

    @Override
    public BlockComposter getBlock() {
        return new BlockComposter();
    }
}
