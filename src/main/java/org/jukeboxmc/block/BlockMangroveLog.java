package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveLog;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveLog extends Block {

    public BlockMangroveLog() {
        super( "minecraft:mangrove_log" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveLog();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_LOG;
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