package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMossBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossBlock extends Item{

    public ItemMossBlock() {
        super( "minecraft:moss_block" );
    }

    @Override
    public BlockMossBlock getBlock() {
        return new BlockMossBlock();
    }
}
