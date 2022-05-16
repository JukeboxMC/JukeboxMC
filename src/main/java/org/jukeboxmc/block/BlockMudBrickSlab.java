package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMudBrickSlab;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBrickSlab extends Block {

    public BlockMudBrickSlab() {
        super( "minecraft:mud_brick_slab" );
    }

    @Override
    public Item toItem() {
        return new ItemMudBrickSlab();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICK_SLAB;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}