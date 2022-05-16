package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMud;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMud extends Block {

    public BlockMud() {
        super( "minecraft:mud" );
    }

    @Override
    public Item toItem() {
        return new ItemMud();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }
}