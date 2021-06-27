package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRawCopperBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRawCopperBlock extends Item{

    public ItemRawCopperBlock() {
        super( "minecraft:raw_copper_block" );
    }

    @Override
    public BlockRawCopperBlock getBlock() {
        return new BlockRawCopperBlock();
    }
}
