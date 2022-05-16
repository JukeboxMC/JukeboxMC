package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveStairs;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveStairs extends BlockStairs {

    public BlockMangroveStairs() {
        super( "minecraft:mangrove_stairs" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_STAIRS;
    }
}