package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleDoor extends BlockDoor {

    public BlockJungleDoor() {
        super( "minecraft:jungle_door" );
    }

    @Override
    public ItemJungleDoor toItem() {
        return new ItemJungleDoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_DOOR;
    }

    @Override
    public BlockDoor newDoor() {
        return new BlockJungleDoor();
    }
}
