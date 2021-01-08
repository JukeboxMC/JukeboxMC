package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoalBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoalBlock extends Item {

    public ItemCoalBlock() {
        super( "minecraft:coal_block", 173 );
    }

    @Override
    public BlockCoalBlock getBlock() {
        return new BlockCoalBlock();
    }
}
