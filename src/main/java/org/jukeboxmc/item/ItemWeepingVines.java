package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWeepingVines;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeepingVines extends Item {

    public ItemWeepingVines() {
        super( -231 );
    }

    @Override
    public BlockWeepingVines getBlock() {
        return new BlockWeepingVines();
    }
}
