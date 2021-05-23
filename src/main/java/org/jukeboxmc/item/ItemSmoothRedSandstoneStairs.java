package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothRedSandstoneStairs extends Item {

    public ItemSmoothRedSandstoneStairs() {
        super( -176 );
    }

    @Override
    public BlockSmoothRedSandstoneStairs getBlock() {
        return new BlockSmoothRedSandstoneStairs();
    }
}
