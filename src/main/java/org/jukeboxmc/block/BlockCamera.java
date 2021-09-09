package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCamera;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCamera extends Block {

    public BlockCamera() {
        super( "minecraft:camera" );
    }

    @Override
    public ItemCamera toItem() {
        return new ItemCamera();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAMERA;
    }

}
