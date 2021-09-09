package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDiamondBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDiamondBlock extends Block {

    public BlockDiamondBlock() {
        super( "minecraft:diamond_block" );
    }

    @Override
    public ItemDiamondBlock toItem() {
        return new ItemDiamondBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIAMOND_BLOCK;
    }

}
