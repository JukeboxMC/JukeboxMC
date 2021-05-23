package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedDoorBlock extends Item {

    public ItemWarpedDoorBlock() {
        super( -245 );
    }

    @Override
    public BlockWarpedDoor getBlock() {
        return new BlockWarpedDoor();
    }
}
