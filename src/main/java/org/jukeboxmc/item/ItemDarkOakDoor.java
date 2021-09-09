package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakDoor extends Item {

    public ItemDarkOakDoor() {
        super ( "minecraft:dark_oak_door" );
    }

    @Override
    public BlockDarkOakDoor getBlock() {
        return new BlockDarkOakDoor();
    }
}
