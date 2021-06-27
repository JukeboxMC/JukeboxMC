package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmallDripleafBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmallDripleafBlock extends Item{

    public ItemSmallDripleafBlock() {
        super( "minecraft:small_dripleaf_block" );
    }

    @Override
    public BlockSmallDripleafBlock getBlock() {
        return new BlockSmallDripleafBlock();
    }
}
