package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBarrel;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBarrel extends Item {

    public ItemBarrel() {
        super ( "minecraft:barrel" );
    }

    @Override
    public BlockBarrel getBlock() {
        return new BlockBarrel();
    }
}
