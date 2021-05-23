package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoorBlock extends Item{

    public ItemCrimsonDoorBlock() {
        super( -244 );
    }

    @Override
    public BlockCrimsonDoor getBlock() {
        return new BlockCrimsonDoor();
    }
}
