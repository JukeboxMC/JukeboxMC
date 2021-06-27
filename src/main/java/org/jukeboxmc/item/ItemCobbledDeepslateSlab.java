package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCobbledDeepslateSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobbledDeepslateSlab extends Item{

    public ItemCobbledDeepslateSlab() {
        super( "minecraft:cobbled_deepslate_slab" );
    }

    @Override
    public BlockCobbledDeepslateSlab getBlock() {
        return new BlockCobbledDeepslateSlab();
    }
}
