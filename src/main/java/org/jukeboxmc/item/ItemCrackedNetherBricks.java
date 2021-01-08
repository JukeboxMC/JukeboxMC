package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrackedNetherBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrackedNetherBricks extends Item {

    public ItemCrackedNetherBricks() {
        super( "minecraft:cracked_nether_bricks", -303 );
    }

    @Override
    public BlockCrackedNetherBricks getBlock() {
        return new BlockCrackedNetherBricks();
    }
}
