package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemMangroveFence;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockMangroveFence extends Block {

    public BlockMangroveFence() {
        super( "minecraft:mangrove_fence" );
    }

    @Override
    public Item toItem() {
        return new ItemMangroveFence();
    }

    @Override
    public BlockType getType() {
        return BlockType.MANGROVE_FENCE;
    }
}