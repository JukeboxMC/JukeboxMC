package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMudBricks;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMudBricks extends Block {

    public BlockMudBricks() {
        super( "minecraft:mud_bricks" );
    }

    @Override
    public Item toItem() {
        return new ItemMudBricks();
    }

    @Override
    public BlockType getType() {
        return BlockType.MUD_BRICKS;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }
}