package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStrippedMangroveWood;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockStrippedMangroveWood extends Block {

    public BlockStrippedMangroveWood() {
        super( "minecraft:stripped_mangrove_wood" );
    }

    @Override
    public Item toItem() {
        return new ItemStrippedMangroveWood();
    }

    @Override
    public BlockType getType() {
        return BlockType.STRIPPED_MANGROVE_WOOD;
    }
}