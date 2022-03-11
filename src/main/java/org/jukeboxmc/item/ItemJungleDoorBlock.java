package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleDoorBlock extends Item implements Burnable {

    public ItemJungleDoorBlock() {
        super ( "minecraft:item.jungle_door" );
    }

    @Override
    public BlockJungleDoor getBlock() {
        return new BlockJungleDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
