package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateBricks extends Item{

    public ItemDeepslateBricks() {
        super( "minecraft:deepslate_bricks" );
    }

    @Override
    public BlockDeepslateBricks getBlock() {
        return new BlockDeepslateBricks();
    }
}
