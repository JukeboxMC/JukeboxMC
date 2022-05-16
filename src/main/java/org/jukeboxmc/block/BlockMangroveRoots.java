package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveRoots;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveRoots extends Block {

    public BlockMangroveRoots() {
        super( "minecraft:mangrove_roots" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveRoots();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_ROOTS;
    }

    @Override
    public double getHardness() {
        return 0.7;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}