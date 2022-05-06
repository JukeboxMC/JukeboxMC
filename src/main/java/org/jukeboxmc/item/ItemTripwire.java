package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTripwire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTripwire extends Item {

    public ItemTripwire() {
        super ( "minecraft:trip_wire" );
    }

    @Override
    public BlockTripwire getBlock() {
        return new BlockTripwire();
    }
}
