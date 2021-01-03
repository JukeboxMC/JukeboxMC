package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoor extends Item {

    public ItemCrimsonDoor() {
        super( "minecraft:crimson_door", 604 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonDoor();
    }
}
