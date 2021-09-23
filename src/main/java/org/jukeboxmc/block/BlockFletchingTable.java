package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemFletchingTable;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFletchingTable extends Block {

    public BlockFletchingTable() {
        super( "minecraft:fletching_table" );
    }

    @Override
    public ItemFletchingTable toItem() {
        return new ItemFletchingTable();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FLETCHING_TABLE;
    }

    @Override
    public double getHardness() {
        return 2.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }
}
