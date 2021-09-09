package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSandstoneStairs extends Item {

    public ItemSandstoneStairs() {
        super ( "minecraft:sandstone_stairs" );
    }

    @Override
    public BlockSandstoneStairs getBlock() {
        return new BlockSandstoneStairs();
    }
}
