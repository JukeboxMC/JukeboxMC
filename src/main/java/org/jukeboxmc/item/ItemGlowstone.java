package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowstone extends Item {

    public ItemGlowstone() {
        super ( "minecraft:glowstone" );
    }

    @Override
    public BlockGlowstone getBlock() {
        return new BlockGlowstone();
    }
}
