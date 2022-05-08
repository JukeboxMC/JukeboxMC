package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHoneycomb;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHoneycombBlock extends Block {

    public BlockHoneycombBlock() {
        super( "minecraft:honeycomb_block" );
    }

    @Override
    public ItemHoneycomb toItem() {
        return new ItemHoneycomb();
    }

    @Override
    public BlockType getType() {
        return BlockType.HONEYCOMB_BLOCK;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

}
