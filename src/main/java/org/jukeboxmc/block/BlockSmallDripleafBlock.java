package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSmallDripleafBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmallDripleafBlock extends Block{

    public BlockSmallDripleafBlock() {
        super( "minecraft:small_dripleaf_block" );
    }

    @Override
    public ItemSmallDripleafBlock toItem() {
        return new ItemSmallDripleafBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMALL_DRIPLEAF_BLOCK;
    }
}
