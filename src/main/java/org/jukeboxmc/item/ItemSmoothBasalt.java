package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothBasalt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothBasalt extends Item{

    public ItemSmoothBasalt() {
        super( "minecraft:smooth_basalt" );
    }

    @Override
    public BlockSmoothBasalt getBlock() {
        return new BlockSmoothBasalt();
    }
}
