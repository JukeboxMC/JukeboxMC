package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedDoor extends Item {

    public ItemWarpedDoor() {
        super( 605 );
    }

    @Override
    public BlockWarpedDoor getBlock() {
        return new BlockWarpedDoor();
    }
}
