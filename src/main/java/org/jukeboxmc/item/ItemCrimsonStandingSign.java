package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonStandingSign extends Item {

    public ItemCrimsonStandingSign() {
        super( -250 );
    }

    @Override
    public BlockCrimsonStandingSign getBlock() {
        return new BlockCrimsonStandingSign();
    }
}
