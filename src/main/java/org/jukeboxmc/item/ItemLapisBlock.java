package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockLapisBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLapisBlock extends Item {

    public ItemLapisBlock() {
        super ( "minecraft:lapis_block" );
    }

    @Override
    public BlockLapisBlock getBlock() {
        return new BlockLapisBlock();
    }
}
