package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockOakDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakDoor extends Item implements Burnable {

    public ItemOakDoor() {
        super ( "minecraft:wooden_door" );
    }

    @Override
    public BlockOakDoor getBlock() {
        return new BlockOakDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
