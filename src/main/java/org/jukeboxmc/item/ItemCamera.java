package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCamera;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCamera extends Item {

    public ItemCamera() {
        super( "minecraft:camera", 582 );
    }

    @Override
    public BlockCamera getBlock() {
        return new BlockCamera();
    }
}
