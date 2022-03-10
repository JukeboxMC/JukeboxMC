package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDarkOakDoor;
import org.jukeboxmc.item.type.Burnable;

import java.time.Duration;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDarkOakDoorBlock extends Item implements Burnable {

    public ItemDarkOakDoorBlock() {
        super ( "minecraft:item.dark_oak_door" );
    }

    @Override
    public BlockDarkOakDoor getBlock() {
        return new BlockDarkOakDoor();
    }

    @Override
    public Duration getBurnTime() {
        return Duration.ofMillis( 200 );
    }
}
