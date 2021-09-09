package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRawGoldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawGoldBlock extends Block{

    public BlockRawGoldBlock() {
        super( "minecraft:raw_gold_block" );
    }

    @Override
    public ItemRawGoldBlock toItem() {
        return new ItemRawGoldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_GOLD_BLOCK;
    }
}
