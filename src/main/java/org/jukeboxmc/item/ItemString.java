package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTripwire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemString extends Item {

    public ItemString() {
        super( 326 );
    }

    @Override
    public BlockTripwire getBlock() {
        return new BlockTripwire(); //Maybe
    }
}
