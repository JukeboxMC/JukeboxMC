package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakDoor extends BlockDoor {

    public BlockDarkOakDoor() {
        super( "minecraft:dark_oak_door" );
    }

    @Override
    public ItemDarkOakDoor toItem() {
        return new ItemDarkOakDoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_DOOR;
    }

    @Override
    public BlockDarkOakDoor newDoor() {
        return new BlockDarkOakDoor();
    }
}
