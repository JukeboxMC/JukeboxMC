package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoorBlock extends Item{

    public ItemCrimsonDoorBlock() {
        super ( "minecraft:item.crimson_door" );
    }

    @Override
    public BlockCrimsonDoor getBlock() {
        return new BlockCrimsonDoor();
    }
}
