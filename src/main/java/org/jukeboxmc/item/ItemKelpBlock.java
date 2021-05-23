package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockKelp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemKelpBlock extends Item {

    public ItemKelpBlock() {
        super( -138 );
    }

    @Override
    public BlockKelp getBlock() {
        return new BlockKelp();
    }
}
