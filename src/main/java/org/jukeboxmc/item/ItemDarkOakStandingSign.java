package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakStandingSign extends Item {

    public ItemDarkOakStandingSign() {
        super( -192 );
    }

    @Override
    public BlockDarkOakStandingSign getBlock() {
        return new BlockDarkOakStandingSign();
    }
}
