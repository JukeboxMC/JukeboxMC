package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemEmeraldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEmeraldBlock extends Block {

    public BlockEmeraldBlock() {
        super( "minecraft:emerald_block" );
    }

    @Override
    public ItemEmeraldBlock toItem() {
        return new ItemEmeraldBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EMERALD_BLOCK;
    }

}
