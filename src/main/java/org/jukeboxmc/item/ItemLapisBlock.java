package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLapisBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLapisBlock extends Item {

    public ItemLapisBlock() {
        super( "minecraft:lapis_block", 22 );
    }

    @Override
    public BlockLapisBlock getBlock() {
        return new BlockLapisBlock();
    }
}
