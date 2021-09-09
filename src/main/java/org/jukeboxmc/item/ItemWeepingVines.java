package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWeepingVines;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeepingVines extends Item {

    public ItemWeepingVines() {
        super ( "minecraft:weeping_vines" );
    }

    @Override
    public BlockWeepingVines getBlock() {
        return new BlockWeepingVines();
    }
}
