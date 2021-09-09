package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRawGoldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRawGoldBlock extends Item{

    public ItemRawGoldBlock() {
        super( "minecraft:raw_gold_block" );
    }

    @Override
    public BlockRawGoldBlock getBlock() {
        return new BlockRawGoldBlock();
    }
}
