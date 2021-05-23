package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBeacon;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeacon extends Item {

    public ItemBeacon() {
        super( 138 );
    }

    @Override
    public BlockBeacon getBlock() {
        return new BlockBeacon();
    }
}
