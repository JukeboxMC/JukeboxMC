package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedDoorBlock extends Item {

    public ItemWarpedDoorBlock() {
        super ( "minecraft:item.warped_door" );
    }

    @Override
    public BlockWarpedDoor getBlock() {
        return new BlockWarpedDoor();
    }
}
