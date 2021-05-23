package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDaylightDetectorInverted;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDaylightDetectorInverted extends Item {

    public ItemDaylightDetectorInverted() {
        super( 178 );
    }

    @Override
    public BlockDaylightDetectorInverted getBlock() {
        return new BlockDaylightDetectorInverted();
    }
}
