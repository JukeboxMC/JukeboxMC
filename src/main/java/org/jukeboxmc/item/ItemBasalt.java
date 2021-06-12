package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBasalt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBasalt extends Item {

    public ItemBasalt() {
        super ( "minecraft:basalt" );
    }

    @Override
    public BlockBasalt getBlock() {
        return new BlockBasalt();
    }
}
