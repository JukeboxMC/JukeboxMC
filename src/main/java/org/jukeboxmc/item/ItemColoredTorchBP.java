package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockColoredTorchBP;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemColoredTorchBP extends Item {

    public ItemColoredTorchBP() {
        super( 204 );
    }

    @Override
    public BlockColoredTorchBP getBlock() {
        return new BlockColoredTorchBP();
    }
}
