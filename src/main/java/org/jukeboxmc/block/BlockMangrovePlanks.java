package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangrovePlanks;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangrovePlanks extends Block {

    public BlockMangrovePlanks() {
        super( "minecraft:mangrove_planks" );
    }

    @Override
    public Item toItem() {
        return new ItemMangrovePlanks();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_PLANKS;
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