package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPolishedDeepslateSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPolishedDeepslateSlab extends Item{

    public ItemPolishedDeepslateSlab() {
        super( "minecraft:polished_deepslate_slab" );
    }

    @Override
    public BlockPolishedDeepslateSlab getBlock() {
        return new BlockPolishedDeepslateSlab();
    }
}
