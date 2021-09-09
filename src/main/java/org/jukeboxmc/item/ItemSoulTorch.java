package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSoulTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSoulTorch extends Item {

    public ItemSoulTorch() {
        super ( "minecraft:soul_torch" );
    }

    @Override
    public BlockSoulTorch getBlock() {
        return new BlockSoulTorch();
    }
}
