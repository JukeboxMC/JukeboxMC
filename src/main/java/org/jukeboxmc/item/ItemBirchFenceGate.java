package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchFenceGate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchFenceGate extends Item {

    public ItemBirchFenceGate() {
        super( 184 );
    }

    @Override
    public BlockBirchFenceGate getBlock() {
        return new BlockBirchFenceGate();
    }
}
