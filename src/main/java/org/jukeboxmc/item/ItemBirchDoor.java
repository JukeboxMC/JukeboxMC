package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchDoor extends Item {

    public ItemBirchDoor() {
        super( "minecraft:birch_door", 544 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchDoor();
    }
}
