package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDripstoneBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDripstoneBlock extends Block{

    public BlockDripstoneBlock() {
        super( "minecraft:dripstone_block" );
    }

    @Override
    public ItemDripstoneBlock toItem() {
        return new ItemDripstoneBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DRIPSTONE_BLOCK;
    }
}
