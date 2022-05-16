package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveWood;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveWood extends Block {

    public BlockMangroveWood() {
        super( "minecraft:mangrove_wood" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveWood();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_WOOD;
    }
}