package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMangroveLeaves;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveLeaves extends Block {

    public BlockMangroveLeaves() {
        super( "minecraft:mangrove_leaves" );
    }

    @Override
    public ItemMangroveLeaves toItem() {
        return new ItemMangroveLeaves();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_LEAVES;
    }
}