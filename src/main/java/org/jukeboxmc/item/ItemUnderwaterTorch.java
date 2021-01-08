package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockUnderwaterTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemUnderwaterTorch extends Item {

    public ItemUnderwaterTorch() {
        super( "minecraft:underwater_torch", 239 );
    }

    @Override
    public BlockUnderwaterTorch getBlock() {
        return new BlockUnderwaterTorch();
    }
}
