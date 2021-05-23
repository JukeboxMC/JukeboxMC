package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemHoneyBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHoneyBlock extends Block {

    public BlockHoneyBlock() {
        super( "minecraft:honey_block" );
    }

    @Override
    public ItemHoneyBlock toItem() {
        return new ItemHoneyBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HONEY_BLOCK;
    }

}
