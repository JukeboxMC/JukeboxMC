package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStandingSign extends Item {

    public ItemStandingSign() {
        super( "minecraft:standing_sign", 63 );
    }

    @Override
    public BlockStandingSign getBlock() {
        return new BlockStandingSign();
    }
}
