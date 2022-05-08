package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronDoor extends BlockDoor {

    public BlockIronDoor() {
        super( "minecraft:iron_door" );
    }

    @Override
    public ItemIronDoor toItem() {
        return new ItemIronDoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.IRON_DOOR;
    }

    @Override
    public BlockDoor newDoor() {
        return new BlockIronDoor();
    }
}
