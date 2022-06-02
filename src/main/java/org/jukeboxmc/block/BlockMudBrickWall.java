package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMudBrickWall;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBrickWall extends Block {

    public BlockMudBrickWall() {
        super( "minecraft:mud_brick_wall" );
    }

    @Override
    public ItemMudBrickWall toItem() {
        return new ItemMudBrickWall();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICK_WALL;
    }
}