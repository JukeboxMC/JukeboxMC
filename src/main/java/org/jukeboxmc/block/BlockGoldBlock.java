package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGoldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGoldBlock extends Block {

    public BlockGoldBlock() {
        super( "minecraft:gold_block" );
    }

    @Override
    public ItemGoldBlock toItem() {
        return new ItemGoldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GOLD_BLOCK;
    }

}
