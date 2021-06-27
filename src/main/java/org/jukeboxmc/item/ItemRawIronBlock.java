package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRawIronBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRawIronBlock extends Item{

    public ItemRawIronBlock() {
        super( "minecraft:raw_iron_block" );
    }

    @Override
    public BlockRawIronBlock getBlock() {
        return new BlockRawIronBlock();
    }
}
