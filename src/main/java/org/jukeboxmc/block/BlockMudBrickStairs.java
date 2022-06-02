package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMudBrickStairs;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBrickStairs extends Block {

    public BlockMudBrickStairs() {
        super( "minecraft:mud_brick_stairs" );
    }

    @Override
    public ItemMudBrickStairs toItem() {
        return new ItemMudBrickStairs();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICK_STAIRS;
    }
}