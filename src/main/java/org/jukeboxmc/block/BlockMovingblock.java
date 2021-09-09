package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMovingblock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMovingblock extends Block {

    public BlockMovingblock() {
        super( "minecraft:movingblock" );
    }

    @Override
    public ItemMovingblock toItem() {
        return new ItemMovingblock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOVING_BLOCK;
    }

}
