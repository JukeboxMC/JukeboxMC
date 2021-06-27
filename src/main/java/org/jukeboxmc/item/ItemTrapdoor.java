package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWoodenTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTrapdoor extends Item {

    public ItemTrapdoor() {
        super ( "minecraft:trapdoor" );
    }

    @Override
    public BlockWoodenTrapdoor getBlock() {
        return new BlockWoodenTrapdoor();
    }
}
