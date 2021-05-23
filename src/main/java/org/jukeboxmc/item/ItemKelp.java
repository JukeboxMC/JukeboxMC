package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockKelp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemKelp extends Item {

    public ItemKelp() {
        super( 380 );
    }

    @Override
    public BlockKelp getBlock() {
        return new BlockKelp();
    }
}
