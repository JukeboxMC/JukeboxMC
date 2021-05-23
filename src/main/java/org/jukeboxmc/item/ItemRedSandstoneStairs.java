package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedSandstoneStairs extends Item {

    public ItemRedSandstoneStairs() {
        super( 180 );
    }

    @Override
    public BlockRedSandstoneStairs getBlock() {
        return new BlockRedSandstoneStairs();
    }
}
