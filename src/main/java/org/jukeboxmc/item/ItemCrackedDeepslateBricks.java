package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrackedDeepslateBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrackedDeepslateBricks extends Item{

    public ItemCrackedDeepslateBricks() {
        super( "minecraft:cracked_deepslate_bricks" );
    }

    @Override
    public BlockCrackedDeepslateBricks getBlock() {
        return new BlockCrackedDeepslateBricks();
    }
}
