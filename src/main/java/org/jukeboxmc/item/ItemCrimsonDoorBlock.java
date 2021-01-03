package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoorBlock extends Item{

    public ItemCrimsonDoorBlock() {
        super( "minecraft:item.crimson_door", -244 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonDoor();
    }
}
