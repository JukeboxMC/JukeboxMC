package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveLeaves;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveLeaves extends Block {

    public BlockMangroveLeaves() {
        super( "minecraft:mangrove_leaves" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveLeaves();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_LEAVES;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHEARS;
    }
}