package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakTrapdoor extends Item {

    public ItemDarkOakTrapdoor() {
        super ( "minecraft:dark_oak_trapdoor" );
    }

    @Override
    public BlockDarkOakTrapdoor getBlock() {
        return new BlockDarkOakTrapdoor();
    }
}
