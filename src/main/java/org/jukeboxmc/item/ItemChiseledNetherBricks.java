package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockChiseledNetherBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChiseledNetherBricks extends Item {

    public ItemChiseledNetherBricks() {
        super( "minecraft:chiseled_nether_bricks", -302 );
    }

    @Override
    public Block getBlock() {
        return new BlockChiseledNetherBricks();
    }
}
