package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateBrickDoubleSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateBrickDoubleSlab extends Item {

    public ItemDeepslateBrickDoubleSlab() {
        super( "minecraft:deepslate_brick_double_slab" );
    }

    @Override
    public BlockDeepslateBrickDoubleSlab getBlock() {
        return new BlockDeepslateBrickDoubleSlab();
    }
}
