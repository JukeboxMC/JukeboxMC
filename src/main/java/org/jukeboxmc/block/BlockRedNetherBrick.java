package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedNetherBrick;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedNetherBrick extends Block {

    public BlockRedNetherBrick() {
        super( "minecraft:red_nether_brick" );
    }

    @Override
    public ItemRedNetherBrick toItem() {
        return new ItemRedNetherBrick();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_NETHER_BRICK;
    }

}
