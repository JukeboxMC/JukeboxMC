package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveTrapdoor;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveTrapdoor extends Block {

    public BlockMangroveTrapdoor() {
        super( "minecraft:mangrove_trapdoor" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveTrapdoor();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_TRAPDOOR;
    }
}