package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemYellowFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockYellowFlower extends Block {

    public BlockYellowFlower() {
        super( "minecraft:yellow_flower" );
    }

    @Override
    public ItemYellowFlower toItem() {
        return new ItemYellowFlower();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.YELLOW_FLOWER;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
