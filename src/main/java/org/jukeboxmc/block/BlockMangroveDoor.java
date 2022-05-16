package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveDoor;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveDoor extends Block {

    public BlockMangroveDoor() {
        super( "minecraft:mangrove_door" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveDoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_DOOR;
    }
}