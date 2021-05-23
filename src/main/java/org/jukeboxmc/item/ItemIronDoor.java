package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronDoor extends Item {

    public ItemIronDoor() {
        super( 370 );
    }

    @Override
    public BlockIronDoor getBlock() {
        return new BlockIronDoor();
    }
}
