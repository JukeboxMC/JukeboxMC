package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMudBrickDoubleSlab;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBrickDoubleSlab extends Block {

    public BlockMudBrickDoubleSlab() {
        super( "minecraft:mud_brick_double_slab" );
    }

    @Override
    public ItemMudBrickDoubleSlab toItem() {
        return new ItemMudBrickDoubleSlab();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICK_DOUBLE_SLAB;
    }
}