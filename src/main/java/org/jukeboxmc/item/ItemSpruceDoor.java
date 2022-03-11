package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceDoor extends Item implements Burnable {

    public ItemSpruceDoor() {
        super ( "minecraft:spruce_door" );
    }

    @Override
    public BlockSpruceDoor getBlock() {
        return new BlockSpruceDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
