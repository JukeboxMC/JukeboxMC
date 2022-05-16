package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemStrippedMangroveLog;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockStrippedMangroveLog extends Block {

    public BlockStrippedMangroveLog() {
        super( "minecraft:stripped_mangrove_log" );
    }

    @Override
    public Item toItem() {
        return new ItemStrippedMangroveLog();
    }

    @Override
    public BlockType getType() {
        return BlockType.STRIPPED_MANGROVE_LOG;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }
}