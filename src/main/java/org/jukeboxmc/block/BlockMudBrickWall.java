package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMudBrickWall;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBrickWall extends Block {

    public BlockMudBrickWall() {
        super( "minecraft:mud_brick_wall" );
    }

    @Override
    public Item toItem() {
        return new ItemMudBrickWall();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICK_WALL;
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