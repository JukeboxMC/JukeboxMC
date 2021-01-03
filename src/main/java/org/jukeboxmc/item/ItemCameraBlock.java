package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCamera;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCameraBlock extends Item {

    public ItemCameraBlock() {
        super( "minecraft:item.camera", 242 );
    }

    @Override
    public Block getBlock() {
        return new BlockCamera();
    }
}
