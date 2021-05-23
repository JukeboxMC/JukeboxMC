package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleFenceGate extends Item {

    public ItemJungleFenceGate() {
        super( 185 );
    }

    @Override
    public BlockJungleFenceGate getBlock() {
        return new BlockJungleFenceGate();
    }
}
