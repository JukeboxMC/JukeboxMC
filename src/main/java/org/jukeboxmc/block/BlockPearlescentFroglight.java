package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPearlescentFroglight;

/**
 * @author Kaooot
 * @version 1.0
 */
public class BlockPearlescentFroglight extends Block {

    public BlockPearlescentFroglight() {
        super( "minecraft:pearlescent_froglight" );
    }

    @Override
    public ItemPearlescentFroglight toItem() {
        return new ItemPearlescentFroglight();
    }

    @Override
    public BlockType getType() {
        return BlockType.PEARLESCENT_FROGLIGHT;
    }
}