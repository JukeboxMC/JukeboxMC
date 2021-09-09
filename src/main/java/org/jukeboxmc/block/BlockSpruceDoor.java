package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSpruceDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceDoor extends BlockDoor {

    public BlockSpruceDoor() {
        super( "minecraft:spruce_door" );
    }

    @Override
    public ItemSpruceDoor toItem() {
        return new ItemSpruceDoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SPRUCE_DOOR;
    }

    @Override
    public BlockSpruceDoor newDoor() {
        return new BlockSpruceDoor();
    }
}
