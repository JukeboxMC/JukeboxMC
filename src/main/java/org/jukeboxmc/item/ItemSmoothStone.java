package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothStone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothStone extends Item {

    public ItemSmoothStone() {
        super( "minecraft:smooth_stone", -183 );
    }

    @Override
    public BlockSmoothStone getBlock() {
        return new BlockSmoothStone();
    }
}
