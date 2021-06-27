package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemRawIronBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRawIronBlock extends Block {
    
    public BlockRawIronBlock() {
        super( "minecraft:raw_iron_block" );
    }

    @Override
    public ItemRawIronBlock toItem() {
        return new ItemRawIronBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RAW_IRON_BLOCK;
    }
}
