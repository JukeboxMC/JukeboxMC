package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronDoorBlock extends Item {

    public ItemIronDoorBlock() {
        super ( "minecraft:item.iron_door" );
    }

    @Override
    public BlockIronDoor getBlock() {
        return new BlockIronDoor();
    }
}
