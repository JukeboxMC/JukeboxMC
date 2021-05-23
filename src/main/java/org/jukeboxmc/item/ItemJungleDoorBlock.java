package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleDoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleDoorBlock extends Item {

    public ItemJungleDoorBlock() {
        super( 195 );
    }

    @Override
    public BlockJungleDoor getBlock() {
        return new BlockJungleDoor();
    }
}
