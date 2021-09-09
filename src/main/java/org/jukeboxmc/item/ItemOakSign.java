package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakSign extends Item {

    public ItemOakSign() {
        super ( "minecraft:oak_sign" );
    }

    @Override
    public BlockStandingSign getBlock() {
        return new BlockStandingSign();
    }
}
