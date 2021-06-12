package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedSandstoneStairs extends Item {

    public ItemRedSandstoneStairs() {
        super ( "minecraft:red_sandstone_stairs" );
    }

    @Override
    public BlockRedSandstoneStairs getBlock() {
        return new BlockRedSandstoneStairs();
    }
}
