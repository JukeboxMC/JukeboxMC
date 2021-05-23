package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchDoor extends Item {

    public ItemBirchDoor() {
        super(  544 );
    }

    @Override
    public BlockBirchDoor getBlock() {
        return new BlockBirchDoor();
    }
}
