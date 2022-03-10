package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchDoor extends Item implements Burnable {

    public ItemBirchDoor() {
        super("minecraft:birch_door" );
    }

    @Override
    public BlockBirchDoor getBlock() {
        return new BlockBirchDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
